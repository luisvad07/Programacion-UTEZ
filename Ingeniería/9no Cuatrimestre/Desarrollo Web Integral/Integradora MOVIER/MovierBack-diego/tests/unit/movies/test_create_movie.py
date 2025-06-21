from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from movies.create_movie import app
from movies.create_movie.utils import get_secret, get_connection


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_success(self, mock_insert_into_movies, mock_movie_exists):
        mock_movie_exists.return_value = False

        mock_event = {
            'body': json.dumps({
                'title': 'New Movie',
                'description': 'A great movie',
                'genre': 'Action',
                'image': 'http://example.com/image.jpg',
                'status': 1
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Película insertada correctamente')

    # Prueba: falta de parámetros en la solicitud
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_missing_parameters(self, mock_insert_into_movies, mock_movie_exists):
        mock_event = {
            'body': json.dumps({
                'title': 'New Movie'
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Faltan parámetros')

    # Prueba: parámetros con longitud incorrecta
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_invalid_parameters(self, mock_insert_into_movies, mock_movie_exists):
        mock_event = {
            'body': json.dumps({
                'title': 'a' * 256,
                'description': 'A great movie',
                'genre': 'Action',
                'image': 'http://example.com/image.jpg',
                'status': 1
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Los parámetros deben ser cadenas de texto de máximo 255 caracteres')

    # Prueba: parámetro status no entero
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_invalid_status_type(self, mock_insert_into_movies, mock_movie_exists):
        mock_event = {
            'body': json.dumps({
                'title': 'New Movie',
                'description': 'A great movie',
                'genre': 'Action',
                'image': 'http://example.com/image.jpg',
                'status': 'not_an_int'
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El parámetro status debe ser un número entero')

    # Prueba: parámetro status fuera de rango
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_invalid_status_range(self, mock_insert_into_movies, mock_movie_exists):
        mock_event = {
            'body': json.dumps({
                'title': 'New Movie',
                'description': 'A great movie',
                'genre': 'Action',
                'image': 'http://example.com/image.jpg',
                'status': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El parámetro status debe ser 0 o 1')

    # Prueba: película ya existe
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_movie_exists(self, mock_insert_into_movies, mock_movie_exists):
        mock_movie_exists.return_value = True

        mock_event = {
            'body': json.dumps({
                'title': 'Existing Movie',
                'description': 'A great movie',
                'genre': 'Action',
                'image': 'http://example.com/image.jpg',
                'status': 1
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La película con el mismo título ya existe')

    # Prueba: error al obtener los parámetros del cuerpo de la solicitud
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    def test_lambda_handler_error_parsing_body(self, mock_insert_into_movies, mock_movie_exists):
        mock_event = {
            'body': "{'title': 'New Movie'}"  # JSON mal formado
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener los parámetros del cuerpo de la solicitud')
        self.assertTrue('error' in body)

    # Prueba: error al verificar la existencia de la película
    @patch("movies.create_movie.app.insert_into_movies")
    @patch("movies.create_movie.app.get_connection")
    def test_movie_exists_error(self, mock_get_connection, mock_insert_into_movies):
        mock_get_connection.return_value = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.side_effect = Exception("Database query error")
        mock_get_connection.return_value.cursor.return_value.__enter__.return_value = mock_cursor

        with self.assertRaises(Exception) as context:
            app.movie_exists('Some Movie')
        self.assertEqual(str(context.exception), "Database query error")

    # Prueba: error al insertar la película
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.get_connection")
    def test_insert_into_movies_error(self, mock_get_connection, mock_movie_exists):
        mock_movie_exists.return_value = False
        mock_get_connection.return_value = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.execute.side_effect = Exception("Database insertion error")
        mock_get_connection.return_value.cursor.return_value.__enter__.return_value = mock_cursor

        with self.assertRaises(Exception) as context:
            app.insert_into_movies('New Movie', 'A great movie', 'Action', 'http://example.com/image.jpg', 1)
        self.assertEqual(str(context.exception), "Database insertion error")

    # Prueba: get_secret con respuesta correcta
    @patch("movies.create_movie.utils.boto3.session.Session.client")
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
    @patch("movies.create_movie.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("movies.create_movie.utils.get_secret")
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
    @patch("movies.create_movie.utils.get_secret")
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
