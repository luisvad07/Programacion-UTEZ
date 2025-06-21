from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from movies.get_movie_by_id import app
from movies.get_movie_by_id.utils import get_secret, get_connection

class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("movies.get_movie_by_id.app.get_movie_by_id")
    def test_lambda_handler_success(self, mock_get_movie_by_id):
        mock_get_movie_by_id.return_value = {
            'id': 1,
            'title': 'Test Movie',
            'description': 'A great movie',
            'genre': 'Action',
            'image': 'http://example.com/image.jpg',
            'status': 1,
            'watched': 1
        }

        mock_event = {
            'pathParameters': {
                'id': '1',
                'user_id': '123'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['Pelicula']['title'], 'Test Movie')

    # Prueba: falta del ID de la película
    @patch("movies.get_movie_by_id.app.get_movie_by_id")
    def test_lambda_handler_missing_movie_id(self, mock_get_movie_by_id):
        mock_event = {
            'pathParameters': {
                'user_id': '123'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener la película de la base de datos')
        self.assertTrue('error' in body)

    # Prueba: falta del ID del usuario
    @patch("movies.get_movie_by_id.app.get_movie_by_id")
    def test_lambda_handler_missing_user_id(self, mock_get_movie_by_id):
        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener la película de la base de datos')
        self.assertTrue('error' in body)

    # Prueba: error al obtener la película
    @patch("movies.get_movie_by_id.app.get_movie_by_id")
    def test_lambda_handler_error_get_movie(self, mock_get_movie_by_id):
        mock_get_movie_by_id.side_effect = Exception("Database error")

        mock_event = {
            'pathParameters': {
                'id': '1',
                'user_id': '123'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener la película de la base de datos')
        self.assertTrue('error' in body)

    # Prueba: película no encontrada
    @patch("movies.get_movie_by_id.app.get_movie_by_id")
    def test_lambda_handler_movie_not_found(self, mock_get_movie_by_id):
        mock_get_movie_by_id.return_value = None

        mock_event = {
            'pathParameters': {
                'id': '1',
                'user_id': '123'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 404)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Película no encontrada')

    # Prueba: get_secret con respuesta correcta
    @patch("movies.get_movie_by_id.utils.boto3.session.Session.client")
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
    @patch("movies.get_movie_by_id.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("movies.get_movie_by_id.utils.get_secret")
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
    @patch("movies.get_movie_by_id.utils.get_secret")
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
