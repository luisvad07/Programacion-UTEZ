import json
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


def lambda_handler(event, __):
    client = boto3.client('cognito-idp', region_name='us-east-1')
    user_pool_id = "us-east-1_tIlTA3kOz"
    client_id = "2iav8vp8gi87dkaf45i03ti7o2"
    try:
        # Parsea el body del evento
        body_parameters = json.loads(event["body"])
        username = body_parameters.get('username')
        temporary_password = body_parameters.get('temporary_password')
        new_password = body_parameters.get('new_password')

        # Autentica al usuario con la contraseña temporal
        response = client.admin_initiate_auth(
            UserPoolId=user_pool_id,
            ClientId=client_id,
            AuthFlow='ADMIN_USER_PASSWORD_AUTH',
            AuthParameters={
                'USERNAME': username,
                'PASSWORD': temporary_password
            }
        )

        if response['ChallengeName'] == 'NEW_PASSWORD_REQUIRED':
            client.respond_to_auth_challenge(
                ClientId=client_id,
                ChallengeName='NEW_PASSWORD_REQUIRED',
                Session=response['Session'],
                ChallengeResponses={
                    'USERNAME': username,
                    'NEW_PASSWORD': new_password,
                    'email_verified': 'true'
                }
            )

            # Llama al método insert_db para actualizar la contraseña en la base de datos
            insert_db(username, new_password)

            return {
                'statusCode': 200,
                'headers': headers_open,
                'body': json.dumps({"message": "Contraseña actualizada exitosamente"})
            }
        else:
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({"error_message": "Error al actualizar la contraseña"})
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


def insert_db(username, password):
    connection = pymysql.connect(host=rds_host, user=rds_user, password=rds_password, db=rds_db)
    try:
        with connection.cursor() as cursor:
            update_query = """
                UPDATE Users SET password = %s WHERE username = %s
            """
            cursor.execute(update_query, (password, username))
            connection.commit()
    finally:
        connection.close()
