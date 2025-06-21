import unittest
from unittest.mock import patch, MagicMock
import json
from botocore.exceptions import ClientError
from login.login_users.app import lambda_handler, select_id


class TestApp(unittest.TestCase):

    @patch('login.login_users.app.pymysql.connect')
    @patch('login.login_users.app.boto3.client')
    def test_lambda_handler_success(self, mock_boto_client, mock_pymysql_connect):
        # Mock Cognito client and response
        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.initiate_auth.return_value = {
            'AuthenticationResult': {
                'IdToken': 'fake_id_token',
                'AccessToken': 'fake_access_token',
                'RefreshToken': 'fake_refresh_token'
            }
        }
        mock_cognito_client.admin_list_groups_for_user.return_value = {
            'Groups': [{'GroupName': 'admin'}]
        }

        # Mock pymysql connection and result
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection = MagicMock()
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_pymysql_connect.return_value = mock_connection

        event = {
            'body': json.dumps({
                'username': 'test_user',
                'password': 'test_password'
            })
        }

        response = lambda_handler(event, None)

        self.assertEqual(response['statusCode'], 200)
        body = json.loads(response['body'])
        self.assertEqual(body['id_token'], 'fake_id_token')
        self.assertEqual(body['access_token'], 'fake_access_token')
        self.assertEqual(body['refresh_token'], 'fake_refresh_token')
        self.assertEqual(body['role'], 'admin')
        self.assertEqual(body['id'], 1)

    @patch('login.login_users.app.pymysql.connect')
    @patch('login.login_users.app.boto3.client')
    def test_lambda_handler_client_error(self, mock_boto_client, mock_pymysql_connect):
        # Mock Cognito client to raise ClientError
        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.initiate_auth.side_effect = ClientError(
            {"Error": {"Message": "ClientError occurred"}}, "InitiateAuth"
        )

        event = {
            'body': json.dumps({
                'username': 'test_user',
                'password': 'test_password'
            })
        }

        response = lambda_handler(event, None)

        self.assertEqual(response['statusCode'], 400)
        body = json.loads(response['body'])
        self.assertEqual(body['error_message'], "ClientError occurred")

    @patch('login.login_users.app.pymysql.connect')
    @patch('login.login_users.app.boto3.client')
    def test_lambda_handler_general_exception(self, mock_boto_client, mock_pymysql_connect):
        # Mock Cognito client to raise a general exception
        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.initiate_auth.side_effect = Exception("General error")

        event = {
            'body': json.dumps({
                'username': 'test_user',
                'password': 'test_password'
            })
        }

        response = lambda_handler(event, None)

        self.assertEqual(response['statusCode'], 500)
        body = json.loads(response['body'])
        self.assertEqual(body['error_message'], "General error")

    @patch('login.login_users.app.pymysql.connect')
    def test_select_id_success(self, mock_pymysql_connect):
        # Mock pymysql connection and result
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection = MagicMock()
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_pymysql_connect.return_value = mock_connection

        result = select_id('test_user')

        self.assertEqual(result, 1)

    @patch('login.login_users.app.pymysql.connect')
    def test_select_id_no_result(self, mock_pymysql_connect):
        # Mock pymysql connection and no result
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = None
        mock_connection = MagicMock()
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_pymysql_connect.return_value = mock_connection

        result = select_id('test_user')

        self.assertIsNone(result)


if __name__ == '__main__':
    unittest.main()
