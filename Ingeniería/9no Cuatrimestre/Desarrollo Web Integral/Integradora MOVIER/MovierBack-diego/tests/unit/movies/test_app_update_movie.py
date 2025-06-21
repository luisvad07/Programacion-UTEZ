from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from movies.update_movie import app
from movies.update_movie.utils import get_secret, get_connection

class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_success(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_get_connection.return_value = MagicMock()
        mock_title_exists.return_value = False
        mock_update_movie.return_value = None

        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'New Title',
                'description': 'New Description',
                'genre': 'New Genre',
                'image': 'New Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Película actualizada correctamente')

    # Prueba: falta el ID de película en la solicitud
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_missing_movie_id(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_event = {
            'pathParameters': {},
            'body': json.dumps({
                'title': 'New Title',
                'description': 'New Description',
                'genre': 'New Genre',
                'image': 'New Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Falta el ID de la película')

    # Prueba: error al obtener el ID de la película
    # @patch("movies.update_movie.app.update_movie")
    # @patch("movies.update_movie.app.title_exists")
    # @patch("movies.update_movie.app.get_connection")
    # def test_lambda_handler_error_getting_movie_id(self, mock_get_connection, mock_title_exists, mock_update_movie):
    #     mock_event = {
    #         'pathParameters': {},
    #         'body': json.dumps({
    #             'title': 'New Title',
    #             'description': 'New Description',
    #             'genre': 'New Genre',
    #             'image': 'New Image URL',
    #             'status': None
    #         })
    #     }
    #
    #     with patch('movies.update_movie.app.json.loads', side_effect=Exception("Error parsing JSON")):
    #         result = app.lambda_handler(mock_event, None)
    #         self.assertEqual(result['statusCode'], 400)
    #         body = json.loads(result['body'])
    #         self.assertEqual(body['message'], 'Error al obtener el ID de la película')
    #         self.assertEqual(body['error'], 'Error parsing JSON')

    # Prueba: falta campos a actualizar
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_missing_update_fields(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({})
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Faltan campos a actualizar')

    # Prueba: título excede 255 caracteres
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_title_exceeds_limit(self, mock_get_connection, mock_title_exists, mock_update_movie):
        long_title = 'a' * 256
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': long_title,
                'description': 'Description',
                'genre': 'Genre',
                'image': 'Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El título no debe exceder los 255 caracteres')

    # Prueba: descripción excede 255 caracteres
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_description_exceeds_limit(self, mock_get_connection, mock_title_exists, mock_update_movie):
        long_description = 'a' * 256
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Title',
                'description': long_description,
                'genre': 'Genre',
                'image': 'Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La descripción no debe exceder los 255 caracteres')

    # Prueba: género excede 255 caracteres
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_genre_exceeds_limit(self, mock_get_connection, mock_title_exists, mock_update_movie):
        long_genre = 'a' * 256
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Title',
                'description': 'Description',
                'genre': long_genre,
                'image': 'Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El género no debe exceder los 255 caracteres')

    # Prueba: URL de imagen excede 255 caracteres
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_image_exceeds_limit(self, mock_get_connection, mock_title_exists, mock_update_movie):
        long_image_url = 'a' * 256
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Title',
                'description': 'Description',
                'genre': 'Genre',
                'image': long_image_url,
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La URL de la imagen no debe exceder los 255 caracteres')

    # Prueba: no se permite actualizar el campo "status"
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_status_not_allowed(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Title',
                'description': 'Description',
                'genre': 'Genre',
                'image': 'Image URL',
                'status': 'Active'
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'No se permite actualizar el campo "status"')

    # Prueba: título ya existe
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_title_exists(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_get_connection.return_value = MagicMock()
        mock_title_exists.return_value = True

        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Existing Title',
                'description': 'Description',
                'genre': 'Genre',
                'image': 'Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La película con el mismo título ya existe')

    # Prueba: error al actualizar la película
    @patch("movies.update_movie.app.update_movie")
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.get_connection")
    def test_lambda_handler_error_updating_movie(self, mock_get_connection, mock_title_exists, mock_update_movie):
        mock_get_connection.return_value = MagicMock()
        mock_title_exists.return_value = False
        mock_update_movie.side_effect = Exception("Database update error")

        mock_event = {
            'pathParameters': {
                'id': '1'
            },
            'body': json.dumps({
                'title': 'Title',
                'description': 'Description',
                'genre': 'Genre',
                'image': 'Image URL',
                'status': None
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al actualizar la película en la base de datos')
        self.assertEqual(body['error'], 'Database update error')

    # Prueba: get_secret con respuesta correcta
    @patch("movies.update_movie.utils.boto3.session.Session.client")
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
    @patch("movies.update_movie.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("movies.update_movie.utils.get_secret")
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
    @patch("movies.update_movie.utils.get_secret")
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

    # Prueba: title_exists con respuesta correcta
    @patch("movies.update_movie.app.get_connection")
    def test_title_exists(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.title_exists("Existing Title", 1)
        self.assertTrue(result)

    # Prueba: title_exists sin resultados
    @patch("movies.update_movie.app.get_connection")
    def test_title_exists_no_results(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (0,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.title_exists("Nonexistent Title", 1)
        self.assertFalse(result)

    # Prueba: update_movie con respuesta correcta
    # @patch("movies.update_movie.app.get_connection")
    # def test_update_movie(self, mock_get_connection):
    #     mock_connection = MagicMock()
    #     mock_cursor = MagicMock()
    #     mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
    #     mock_get_connection.return_value = mock_connection
    #
    #     app.update_movie(1, "Title", "Description", "Genre", "Image URL", None)
    #     mock_cursor.execute.assert_called_with("""
    #         UPDATE Movies
    #         SET title = %s, description = %s, genre = %s, image = %s
    #         WHERE id = %s
    #     """, ("Title", "Description", "Genre", "Image URL", 1))
    #     mock_connection.commit.assert_called_once()

    # Prueba: update_movie con error de actualización
    @patch("movies.update_movie.app.get_connection")
    def test_update_movie_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_connection.cursor.return_value.__enter__.side_effect = Exception("Update error")
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.update_movie(1, "Title", "Description", "Genre", "Image URL", None)
        self.assertEqual(str(context.exception), "Update error")

if __name__ == '__main__':
    unittest.main()
