from unittest.mock import patch, MagicMock
import unittest
import json
import random
import string
from botocore.exceptions import ClientError
from login.insert_user_pool import app


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso de creación de usuario
    @patch("login.insert_user_pool.app.insert_db")
    @patch("login.insert_user_pool.app.generate_temporary_password")
    @patch("login.insert_user_pool.app.boto3.client")
    def test_lambda_handler_success(self, mock_boto_client, mock_generate_password, mock_insert_db):
        # Configuración de los mocks
        mock_generate_password.return_value = "TempPass123!"

        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.admin_create_user.return_value = {}
        mock_cognito_client.admin_add_user_to_group.return_value = {}

        mock_insert_db.return_value = None

        mock_event = {
            "body": json.dumps({
                "user_name": "user@example.com",
                "username": "testuser"
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], "Usuario creado exitosamente, revise su correo para obtener su contraseña")

    # Prueba: falta de parámetros en la solicitud
    @patch("login.insert_user_pool.app.insert_db")
    @patch("login.insert_user_pool.app.generate_temporary_password")
    @patch("login.insert_user_pool.app.boto3.client")
    def test_lambda_handler_missing_parameters(self, mock_boto_client, mock_generate_password, mock_insert_db):
        mock_event = {
            "body": json.dumps({})
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], "Faltan parametros")

    # Prueba: error en Cognito (ClientError)
    @patch("login.insert_user_pool.app.insert_db")
    @patch("login.insert_user_pool.app.generate_temporary_password")
    @patch("login.insert_user_pool.app.boto3.client")
    def test_lambda_handler_cognito_error(self, mock_boto_client, mock_generate_password, mock_insert_db):
        mock_generate_password.return_value = "TempPass123!"

        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.admin_create_user.side_effect = ClientError(
            {"Error": {"Message": "Cognito error"}},
            "AdminCreateUser"
        )

        mock_insert_db.return_value = None

        mock_event = {
            "body": json.dumps({
                "user_name": "user@example.com",
                "username": "testuser"
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['error_message'], "Cognito error")

    # Prueba: error en la base de datos
    @patch("login.insert_user_pool.app.insert_db")
    @patch("login.insert_user_pool.app.generate_temporary_password")
    @patch("login.insert_user_pool.app.boto3.client")
    def test_lambda_handler_db_error(self, mock_boto_client, mock_generate_password, mock_insert_db):
        mock_generate_password.return_value = "TempPass123!"

        mock_cognito_client = MagicMock()
        mock_boto_client.return_value = mock_cognito_client
        mock_cognito_client.admin_create_user.return_value = {}
        mock_cognito_client.admin_add_user_to_group.return_value = {}

        mock_insert_db.side_effect = Exception("Database error")

        mock_event = {
            "body": json.dumps({
                "user_name": "user@example.com",
                "username": "testuser"
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['error_message'], "Database error")

    # Prueba: generación de contraseña temporal
    def test_generate_temporary_password(self):
        password = app.generate_temporary_password()
        self.assertTrue(any(char.isdigit() for char in password))
        self.assertTrue(any(char.isupper() for char in password))
        self.assertTrue(any(char.islower() for char in password))
        self.assertTrue(any(char in '^$*.[]{}()?-"!@#%&/\\,><\':;|_~+= ' for char in password))
        self.assertGreaterEqual(len(password), 8)

    # Prueba: generación de contraseña temporal con longitud específica
    def test_generate_temporary_password_length(self):
        password = app.generate_temporary_password(length=16)
        self.assertEqual(len(password), 16)


if __name__ == '__main__':
    unittest.main()
