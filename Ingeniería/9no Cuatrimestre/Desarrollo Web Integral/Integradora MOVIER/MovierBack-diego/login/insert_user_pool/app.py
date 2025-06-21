import json
import os
import random
import string
import boto3
import pymysql
from botocore.exceptions import ClientError

rds_host = "movier.cpiae0u0ckf8.us-east-1.rds.amazonaws.com"
rds_user = "MovierAdmin"
rds_password = "4dmin123"
rds_db = "movier"

headers_open = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    }
def lambda_handler(event, context):
    body_parameters = json.loads(event["body"])
    email = body_parameters.get('user_name')
    username = body_parameters.get('username')
    password = generate_temporary_password()
    role = "Usuario"

    if email is None or username is None:
        return {
            "statusCode": 400,
            'headers': headers_open,
            "body": json.dumps({"message": "Faltan parametros"})
        }

    try:
        # Configura el cliente de Cognito
        client = boto3.client('cognito-idp', region_name='us-east-1')
        user_pool_id = "us-east-1_AmpHw9yS0"

        # Crea el usuario con correo no verificado y contraseña temporal que se envia automaticamente a su correo
        client.admin_create_user(
            UserPoolId=user_pool_id,
            Username=username,
            UserAttributes=[
                {'Name': 'email', 'Value': email},
                {'Name': 'email_verified', 'Value': 'false'},
            ],
            TemporaryPassword=password
        )

        client.admin_add_user_to_group(
            UserPoolId=user_pool_id,
            Username=username,
            GroupName=role
        )

        insert_db(username, email, password, 'user')

        return {
            'statusCode': 200,
            'headers': headers_open,
            'body': json.dumps({"message": "Usuario creado exitosamente, revise su correo para obtener su contraseña"})
        }

    except ClientError as e:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({"error_message": e.response['Error']['Message']})
        }
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({"error_message": str(e)})
        }


def insert_db(username, email, password, role):
    connection = pymysql.connect(host=rds_host, user=rds_user, password=rds_password, db=rds_db)
    try:
        with connection.cursor() as cursor:
            insert_query = """
                INSERT INTO Users (username, email, password, role) VALUES (%s, %s, %s, %s)
            """
            cursor.execute(insert_query, (username, email, password, role))
            connection.commit()
    finally:
        connection.close()


def generate_temporary_password(length=12):
    """Genera una contraseña temporal segura"""
    special_characters = '^$*.[]{}()?-"!@#%&/\\,><\':;|_~+= '
    characters = string.ascii_letters + string.digits + special_characters

    while True:
        # Genera una contraseña aleatoria
        password = ''.join(random.choice(characters) for _ in range(length))

        # Verifica los criterios
        has_digit = any(char.isdigit() for char in password)
        has_upper = any(char.isupper() for char in password)
        has_lower = any(char.islower() for char in password)
        has_special = any(char in special_characters for char in password)

        if has_digit and has_upper and has_lower and has_special and len(password) >= 8:
            return password
