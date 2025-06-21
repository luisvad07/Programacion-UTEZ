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
    client_id = "7ss3ku3uarreptpl5eg5khksoj"

    try:
        body_parameters = json.loads(event["body"])
        username = body_parameters.get('username')
        password = body_parameters.get('password')

        response = client.initiate_auth(
            ClientId=client_id,
            AuthFlow='USER_PASSWORD_AUTH',
            AuthParameters={
                'USERNAME': username,
                'PASSWORD': password
            }
        )

        # Log the response for debugging
        print("Cognito response: ", response)

        if 'AuthenticationResult' not in response:
            raise Exception("AuthenticationResult not in response")

        id_token = response['AuthenticationResult']['IdToken']
        access_token = response['AuthenticationResult']['AccessToken']
        refresh_token = response['AuthenticationResult']['RefreshToken']

        # Get user groups
        user_groups = client.admin_list_groups_for_user(
            Username=username,
            UserPoolId='us-east-1_AmpHw9yS0'  # Replace with your User Pool ID
        )

        # Determine the role based on the group
        role = None
        if user_groups['Groups']:
            role = user_groups['Groups'][0]['GroupName']  # Assuming a user belongs to a single group
        IdUser = select_id(username)

        return {
            'statusCode': 200,
            'headers': headers_open,
            'body': json.dumps({
                'id_token': id_token,
                'access_token': access_token,
                'refresh_token': refresh_token,
                'role': role,
                'id': IdUser
            }),
        }

    except ClientError as e:
        print(f"ClientError: {e.response['Error']['Message']}")
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({"error_message": e.response['Error']['Message']})
        }
    except Exception as e:
        print(f"Exception: {str(e)}")
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({"error_message": str(e)})
        }


def select_id(username):
    connection = pymysql.connect(host=rds_host, user=rds_user, password=rds_password, db=rds_db)
    try:
        with connection.cursor() as cursor:
            select_query = "SELECT id FROM Users WHERE username = %s"
            cursor.execute(select_query, (username,))
            result = cursor.fetchone()
            if result:
                return result[0]
            else:
                return None
    finally:
        connection.close()
