from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError
from comments.get_comments import app
from comments.get_comments.utils import get_secret, get_connection


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_success(self, mock_get_connection, mock_movie_exists, mock_get_comments_with_movie_id):
        mock_get_connection.return_value = MagicMock()
        mock_movie_exists.return_value = True
        mock_get_comments_with_movie_id.return_value = [
            {'comment_id': 1, 'user_id': 1, 'movie_id': 1, 'comment': 'Great movie!', 'date': '2024-08-20 12:00:00',
             'username': 'user1'}
        ]

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(len(body['Comentarios']), 1)
        self.assertEqual(body['Comentarios'][0]['comment'], 'Great movie!')

    # Prueba: falta el parámetro movie_id
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_missing_movie_id(self, mock_get_connection, mock_movie_exists,
                                             mock_get_comments_with_movie_id):
        mock_event = {
            'pathParameters': {}
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Falta el parámetro movie_id')

    # Prueba: parámetro movie_id no es un entero positivo
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_invalid_movie_id(self, mock_get_connection, mock_movie_exists,
                                             mock_get_comments_with_movie_id):
        mock_event = {
            'pathParameters': {
                'id': '-1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El parámetro movie_id debe ser un entero positivo')

    # Prueba: película no existe
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_movie_not_exists(self, mock_get_connection, mock_movie_exists,
                                             mock_get_comments_with_movie_id):
        mock_get_connection.return_value = MagicMock()
        mock_movie_exists.return_value = False

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La película no existe')

    # Prueba: error al verificar la existencia de la película
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_error_checking_movie_exists(self, mock_get_connection, mock_movie_exists,
                                                        mock_get_comments_with_movie_id):
        mock_get_connection.return_value = MagicMock()
        mock_movie_exists.side_effect = Exception("Database error")

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al verificar la existencia de la película')
        self.assertEqual(body['error'], 'Database error')

    # Prueba: error al obtener los comentarios
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("comments.get_comments.app.get_connection")
    def test_lambda_handler_error_getting_comments(self, mock_get_connection, mock_movie_exists,
                                                   mock_get_comments_with_movie_id):
        mock_get_connection.return_value = MagicMock()
        mock_movie_exists.return_value = True
        mock_get_comments_with_movie_id.side_effect = Exception("Database query error")

        mock_event = {
            'pathParameters': {
                'id': '1'
            }
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al obtener los comentarios de la base de datos')
        self.assertEqual(body['error'], 'Database query error')

    # Prueba: get_secret con respuesta correcta
    @patch("comments.get_comments.utils.boto3.session.Session.client")
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
    @patch("comments.get_comments.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("comments.get_comments.utils.get_secret")
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
    @patch("comments.get_comments.utils.get_secret")
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

    # Prueba: movie_exists con respuesta correcta
    @patch("comments.get_comments.app.get_connection")
    def test_movie_exists(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.movie_exists(1)
        self.assertTrue(result)

    # Prueba: movie_exists con película no encontrada
    @patch("comments.get_comments.app.get_connection")
    def test_movie_exists_not_found(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (0,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.movie_exists(1)
        self.assertFalse(result)

    # Prueba: movie_exists con error de consulta
    @patch("comments.get_comments.app.get_connection")
    def test_movie_exists_query_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_connection.cursor.side_effect = Exception("Query execution error")
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.movie_exists(1)
        self.assertEqual(str(context.exception), "Query execution error")

    # Prueba: get_comments_with_movie_id con respuesta correcta
    # @patch("comments.get_comments.app.get_connection")
    # def test_get_comments_with_movie_id(self, mock_get_connection):
    #     mock_connection = MagicMock()
    #     mock_cursor = MagicMock()
    #     mock_cursor.fetchall.return_value = [
    #         (1, 1, 1, 'Great movie!', '2024-08-20 12:00:00', 'user1')
    #     ]
    #     mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
    #     mock_get_connection.return_value = mock_connection
    #
    #     result = app.get_comments_with_movie_id(1)
    #     self.assertEqual(len(result), 1)
    #     self.assertEqual(result[0]['comment'], 'Great movie!')

    # Prueba: get_comments_with_movie_id con error de consulta
    @patch("comments.get_comments.app.get_connection")
    def test_get_comments_with_movie_id_query_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_connection.cursor.side_effect = Exception("Query execution error")
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.get_comments_with_movie_id(1)
        self.assertEqual(str(context.exception), "Query execution error")


if __name__ == '__main__':
    unittest.main()
