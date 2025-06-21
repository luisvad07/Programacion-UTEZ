from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from movies.get_movies import app
from movies.get_movies.utils import get_secret, get_connection


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("movies.get_movies.app.get_movies_with_status")
    @patch("movies.get_movies.app.get_connection")
    def test_lambda_handler_success(self, mock_get_connection, mock_get_movies_with_status):
        mock_get_connection.return_value = MagicMock()
        mock_get_movies_with_status.return_value = [
            {'id': 1, 'title': 'Movie 1', 'description': 'Description 1', 'genre': 'Genre 1', 'image': 'Image 1',
             'status': 1},
            {'id': 2, 'title': 'Movie 2', 'description': 'Description 2', 'genre': 'Genre 2', 'image': 'Image 2',
             'status': 1}
        ]

        mock_event = {}
        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(len(body['Peliculas']), 2)
        self.assertEqual(body['Peliculas'][0]['title'], 'Movie 1')

    # Prueba: error al obtener las películas de la base de datos
    @patch("movies.get_movies.app.get_movies_with_status")
    @patch("movies.get_movies.app.get_connection")
    def test_lambda_handler_error_getting_movies(self, mock_get_connection, mock_get_movies_with_status):
        mock_get_connection.return_value = MagicMock()
        mock_get_movies_with_status.side_effect = Exception("Database query error")

        mock_event = {}
        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener las películas de la base de datos')
        self.assertEqual(body['error'], 'Database query error')

    # Prueba: get_movies_with_status con respuesta correcta
    @patch("movies.get_movies.app.get_connection")
    def test_get_movies_with_status(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchall.return_value = [
            (1, 'Movie 1', 'Description 1', 'Genre 1', 'Image 1', 1),
            (2, 'Movie 2', 'Description 2', 'Genre 2', 'Image 2', 1)
        ]
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.get_movies_with_status(1)
        self.assertEqual(len(result), 2)
        self.assertEqual(result[0]['title'], 'Movie 1')
        self.assertEqual(result[1]['title'], 'Movie 2')

    # Prueba: get_movies_with_status con error de consulta
    @patch("movies.get_movies.app.get_connection")
    def test_get_movies_with_status_query_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_connection.cursor.side_effect = Exception("Query execution error")
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.get_movies_with_status(1)
        self.assertEqual(str(context.exception), "Query execution error")

    # Prueba: get_secret con respuesta correcta
    @patch("movies.get_movies.utils.boto3.session.Session.client")
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
    @patch("movies.get_movies.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("movies.get_movies.utils.get_secret")
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
    @patch("movies.get_movies.utils.get_secret")
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


if __name__ == '__main__':
    unittest.main()
