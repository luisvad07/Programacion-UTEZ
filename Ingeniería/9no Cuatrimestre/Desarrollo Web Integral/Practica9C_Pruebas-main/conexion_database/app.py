import boto3
import pymysql
import os
import json

secrets_client = boto3.client('secretsmanager')


def get_secret(secret_name):
    secret = secrets_client.get_secret_value(SecretId=secret_name)
    return json.loads(secret['SecretString'])


def lambda_handler(event, context):
    secret = get_secret('myapp/DatabaseCredentials')

    db_host = secret['host']
    db_user = secret['username']
    db_pass = secret['password']
    db_name = secret['dbname']

    try:
        connection = pymysql.connect(
            host=db_host,
            user=db_user,
            password=db_pass,
            database=db_name
        )
        cursor = connection.cursor()

        cursor.execute("SELECT NOW()")
        result = cursor.fetchone()

        cursor.close()
        connection.close()

        return {
            'statusCode': 200,
            'body': {
                'message': 'Query executed successfully',
                'result': result
            }
        }

    except pymysql.MySQLError as e:
        return {
            'statusCode': 500,
            'body': {
                'message': 'Database connection failed',
                'error': str(e)
            }
        }
    except Exception as e:
        return {
            'statusCode': 500,
            'body': {
                'message': 'An error occurred',
                'error': str(e)
            }
        }
