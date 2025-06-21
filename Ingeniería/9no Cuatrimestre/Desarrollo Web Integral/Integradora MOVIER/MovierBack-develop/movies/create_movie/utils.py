import json
import boto3
import pymysql as PyMySQL

import base64
from botocore.exceptions import ClientError


def get_secret():
    secret_name = "prd/dataBase/appMovier"
    region_name = "us-east-1"

    # Create a Secrets Manager client
    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name=region_name
    )

    try:
        get_secret_value_response = client.get_secret_value(
            SecretId=secret_name
        )
    except ClientError as e:
        raise e

    # Decrypts secret using the associated KMS CMK.
    if 'SecretString' in get_secret_value_response:
        secret = get_secret_value_response['SecretString']
        secret_dict = json.loads(secret)
    else:
        decoded_binary_secret = base64.b64decode(get_secret_value_response['SecretBinary'])
        secret_dict = json.loads(decoded_binary_secret)

    return secret_dict


def get_connection():
    secret = get_secret()
    return PyMySQL.connect(
        host=secret["host"],
        user=secret["username"],
        password=secret["password"],
        database=secret["dbInstanceIdentifier"],
        port=secret["port"]
    )
