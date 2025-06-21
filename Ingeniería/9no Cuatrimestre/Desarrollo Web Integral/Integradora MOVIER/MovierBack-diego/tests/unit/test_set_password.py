import json
import unittest
from unittest.mock import patch, MagicMock
from login.set_password.app import lambda_handler, insert_db
from botocore.exceptions import ClientError


class TestApp(unittest.TestCase):

    @patch('login.set_password.app.pymysql.connect')
    @patch('login.set_password.app.boto3.client')
    def test_lambda_handler_success(self, mock_boto_client, mock_pymysql_connect):
        # Mock para boto3 client
        mock_client = MagicMock()
        mock_boto_client.return_value = mock_client
        mock_client.admin_initiate_auth.return_value = {
            'ChallengeName': 'NEW_PASSWORD_REQUIRED',
            'Session': 'fake_session'
        }
        mock_client.respond_to_auth_challenge.return_value = {}

        # Mock para pymysql connect
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_pymysql_connect.return_value = mock_connection
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Parametros de entrada para la Lambda
        event = {
            "body": json.dumps({
                "username": "testuser",
                "temporary_password": "tempPassword",
                "new_password": "newPassword"
            })
        }

        # Llamada a lambda_handler
        response = lambda_handler(event, None)

        # Aserciones
        self.assertEqual(response['statusCode'], 200)
        self.assertEqual(json.loads(response['body'])['message'], "Contraseña actualizada exitosamente")

        # Verifica que insert_db fue llamado con los parametros correctos
        mock_connection.cursor.assert_called_once()
        self.assertEqual(
            mock_cursor.execute.call_args[0][0].strip(),
            "UPDATE Users SET password = %s WHERE username = %s"
        )
        self.assertEqual(
            mock_cursor.execute.call_args[0][1],
            ("newPassword", "testuser")
        )

    @patch('login.set_password.app.pymysql.connect')
    @patch('login.set_password.app.boto3.client')
    def test_lambda_handler_challenge_error(self, mock_boto_client, mock_pymysql_connect):
        # Mock para boto3 client
        mock_client = MagicMock()
        mock_boto_client.return_value = mock_client
        mock_client.admin_initiate_auth.return_value = {
            'ChallengeName': 'OTHER_CHALLENGE'
        }

        # Parametros de entrada para la Lambda
        event = {
            "body": json.dumps({
                "username": "testuser",
                "temporary_password": "tempPassword",
                "new_password": "newPassword"
            })
        }

        # Llamada a lambda_handler
        response = lambda_handler(event, None)

        # Aserciones
        self.assertEqual(response['statusCode'], 400)
        self.assertEqual(json.loads(response['body'])['error_message'], "Error al actualizar la contraseña")

    @patch('login.set_password.app.pymysql.connect')
    @patch('login.set_password.app.boto3.client')
    def test_lambda_handler_client_error(self, mock_boto_client, mock_pymysql_connect):
        # Mock para boto3 client
        mock_client = MagicMock()
        mock_boto_client.return_value = mock_client
        mock_client.admin_initiate_auth.side_effect = ClientError(
            {"Error": {"Message": "Client error occurred"}},
            "admin_initiate_auth"
        )

        # Parametros de entrada para la Lambda
        event = {
            "body": json.dumps({
                "username": "testuser",
                "temporary_password": "tempPassword",
                "new_password": "newPassword"
            })
        }

        # Llamada a lambda_handler
        response = lambda_handler(event, None)

        # Aserciones
        self.assertEqual(response['statusCode'], 400)
        self.assertEqual(json.loads(response['body'])['error_message'], "Client error occurred")

    @patch('login.set_password.app.pymysql.connect')
    @patch('login.set_password.app.boto3.client')
    def test_lambda_handler_general_exception(self, mock_boto_client, mock_pymysql_connect):
        # Mock para boto3 client
        mock_client = MagicMock()
        mock_boto_client.return_value = mock_client
        mock_client.admin_initiate_auth.side_effect = Exception("General error occurred")

        # Parametros de entrada para la Lambda
        event = {
            "body": json.dumps({
                "username": "testuser",
                "temporary_password": "tempPassword",
                "new_password": "newPassword"
            })
        }

        # Llamada a lambda_handler
        response = lambda_handler(event, None)

        # Aserciones
        self.assertEqual(response['statusCode'], 500)
        self.assertEqual(json.loads(response['body'])['error_message'], "General error occurred")

    @patch('login.set_password.app.pymysql.connect')
    def test_insert_db(self, mock_pymysql_connect):
        # Mock para pymysql connect
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_pymysql_connect.return_value = mock_connection
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Llamada a insert_db
        insert_db("testuser", "newPassword")

        # Verificaciones
        mock_connection.cursor.assert_called_once()
        self.assertEqual(
            mock_cursor.execute.call_args[0][0].strip(),
            "UPDATE Users SET password = %s WHERE username = %s"
        )
        self.assertEqual(
            mock_cursor.execute.call_args[0][1],
            ("newPassword", "testuser")
        )


if __name__ == '__main__':
    unittest.main()
