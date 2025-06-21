from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from watched.get_watched_movies_user import app
from watched.get_watched_movies_user.utils import get_secret, get_connection


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("watched.get_watched_movies_user.app.get_movies_watched_user")
    @patch("watched.get_watched_movies_user.app.get_connection")
    def test_lambda_handler_success(self, mock_get_connection, mock_get_movies_watched_user):
        mock_get_connection.return_value = MagicMock()
        mock_get_movies_watched_user.return_value = [
            {'movie_id': 1, 'title': 'Movie 1', 'description': 'Description 1', 'genre': 'Genre 1', 'image': 'Image 1'},
            {'movie_id': 2, 'title': 'Movie 2', 'description': 'Description 2', 'genre': 'Genre 2', 'image': 'Image 2'}
        ]

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(len(body), 2)
        self.assertEqual(body[0]['title'], 'Movie 1')

    # Prueba: falta el ID de usuario en la solicitud
    @patch("watched.get_watched_movies_user.app.get_movies_watched_user")
    @patch("watched.get_watched_movies_user.app.get_connection")
    def test_lambda_handler_missing_user_id(self, mock_get_connection, mock_get_movies_watched_user):
        mock_event = {
            'pathParameters': {}
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Falta el ID de usuario en la solicitud')

    # Prueba: error al obtener el ID de usuario de la solicitud
    # @patch("watched.get_watched_movies_user.app.get_movies_watched_user")
    # @patch("watched.get_watched_movies_user.app.get_connection")
    # def test_lambda_handler_error_getting_user_id(self, mock_get_connection, mock_get_movies_watched_user):
    #     mock_event = {
    #         'pathParameters': {}
    #     }
    #     with patch('watched.get_watched_movies_user.app.json.loads', side_effect=Exception("Error parsing JSON")):
    #         result = app.lambda_handler(mock_event, None)
    #         self.assertEqual(result['statusCode'], 400)
    #         body = json.loads(result['body'])
    #         self.assertEqual(body['message'], 'Error al obtener el ID de usuario de la solicitud')
    #         self.assertEqual(body['error'], 'Error parsing JSON')

    # Prueba: error al obtener las películas vistas del usuario
    @patch("watched.get_watched_movies_user.app.get_movies_watched_user")
    @patch("watched.get_watched_movies_user.app.get_connection")
    def test_lambda_handler_error_getting_movies(self, mock_get_connection, mock_get_movies_watched_user):
        mock_get_connection.return_value = MagicMock()
        mock_get_movies_watched_user.side_effect = Exception("Database query error")

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener las películas vistas del usuario')
        self.assertEqual(body['error'], 'Database query error')

    # Prueba: get_secret con respuesta correcta
    @patch("watched.get_watched_movies_user.utils.boto3.session.Session.client")
    def test_get_secret(self, mock_boto_client):
        mock_secret_string = json.dumps({
            "host": "localhost",
            "username": "test_user",
            "password": "test_pass",
            "dbInstanceIdentifier": "test_db",
            "port": 3306
        })

        mock_client = MagicMock()
        mock_client.get_secret_value.return_value = {
            'SecretString': mock_secret_string
        }
        mock_boto_client.return_value = mock_client

        result = get_secret()
        self.assertEqual(result['host'], "localhost")
        self.assertEqual(result['username'], "test_user")
        self.assertEqual(result['password'], "test_pass")
        self.assertEqual(result['dbInstanceIdentifier'], "test_db")
        self.assertEqual(result['port'], 3306)

    # Prueba: get_secret con ClientError
    @patch("watched.get_watched_movies_user.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("watched.get_watched_movies_user.utils.get_secret")
    @patch("pymysql.connect")
    def test_get_connection(self, mock_connect, mock_get_secret):
        mock_get_secret.return_value = {
            "host": "localhost",
            "username": "test_user",
            "password": "test_pass",
            "dbInstanceIdentifier": "test_db",
            "port": 3306
        }

        mock_connect.return_value = MagicMock()
        connection = get_connection()
        mock_connect.assert_called_with(
            host="localhost",
            user="test_user",
            password="test_pass",
            database="test_db",
            port=3306
        )
        self.assertIsNotNone(connection)

    # Prueba: get_connection con error de conexión
    @patch("watched.get_watched_movies_user.utils.get_secret")
    @patch("pymysql.connect")
    def test_get_connection_error(self, mock_connect, mock_get_secret):
        mock_get_secret.return_value = {
            "host": "localhost",
            "username": "test_user",
            "password": "test_pass",
            "dbInstanceIdentifier": "test_db",
            "port": 3306
        }
        mock_connect.side_effect = Exception("Database connection error")

        with self.assertRaises(Exception) as context:
            get_connection()
        self.assertEqual(str(context.exception), "Database connection error")

    # Prueba: get_movies_watched_user con respuesta correcta
    @patch("watched.get_watched_movies_user.app.get_connection")
    def test_get_movies_watched_user(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchall.return_value = [
            (1, 'Movie 1', 'Description 1', 'Genre 1', 'Image 1'),
            (2, 'Movie 2', 'Description 2', 'Genre 2', 'Image 2')
        ]
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.get_movies_watched_user(1)
        self.assertEqual(len(result), 2)
        self.assertEqual(result[0]['title'], 'Movie 1')
        self.assertEqual(result[1]['title'], 'Movie 2')

    # Prueba: get_movies_watched_user con error de consulta
    @patch("watched.get_watched_movies_user.app.get_connection")
    def test_get_movies_watched_user_query_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_connection.cursor.side_effect = Exception("Query execution error")
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.get_movies_watched_user(1)
        self.assertEqual(str(context.exception), "Query execution error")


if __name__ == '__main__':
    unittest.main()
