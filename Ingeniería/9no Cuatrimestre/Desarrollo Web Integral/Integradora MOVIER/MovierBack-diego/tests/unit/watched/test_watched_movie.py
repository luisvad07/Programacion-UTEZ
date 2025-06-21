from unittest.mock import patch, MagicMock
import unittest
import json
from botocore.exceptions import ClientError

from watched.watched_movie import app
from watched.watched_movie.utils import get_secret, get_connection


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso cuando la película se marca como vista
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_success_marked(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = True
        mock_mark_movie_as_watched.return_value = False

        mock_event = {
            'body': json.dumps({
                'user_id': 1,
                'movie_id': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Película marcada como vista con éxito')

    # Prueba: caso exitoso cuando la película se desmarca como vista
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_success_unmarked(self, mock_is_valid_user, mock_is_active_movie,
                                             mock_mark_movie_as_watched):
        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = True
        mock_mark_movie_as_watched.return_value = True

        mock_event = {
            'body': json.dumps({
                'user_id': 1,
                'movie_id': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Película desmarcada como vista')

    # Prueba: falta user_id o movie_id en el cuerpo de la solicitud
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_missing_params(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_event = {
            'body': json.dumps({})
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Los IDs de usuario y película deben ser enteros')

    # Prueba: IDs no son enteros
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_invalid_ids(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_event = {
            'body': json.dumps({
                'user_id': 'not_an_int',
                'movie_id': 'not_an_int'
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Los IDs de usuario y película deben ser enteros')

    # Prueba: usuario no válido
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_invalid_user(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_is_valid_user.return_value = False

        mock_event = {
            'body': json.dumps({
                'user_id': 1,
                'movie_id': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'El usuario no existe o no es válido')

    # Prueba: película no activa
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_inactive_movie(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = False

        mock_event = {
            'body': json.dumps({
                'user_id': 1,
                'movie_id': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'La película no existe o no está activa')

    # Prueba: error al marcar la película como vista
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_error(self, mock_is_valid_user, mock_is_active_movie, mock_mark_movie_as_watched):
        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = True
        mock_mark_movie_as_watched.side_effect = Exception("Database error")

        mock_event = {
            'body': json.dumps({
                'user_id': 1,
                'movie_id': 2
            })
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], 'Error al marcar la película como vista en la base de datos')
        self.assertEqual(body['error'], 'Database error')

    # Prueba: get_secret con respuesta correcta
    @patch("watched.watched_movie.utils.boto3.session.Session.client")
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
    @patch("watched.watched_movie.utils.boto3.session.Session.client")
    def test_get_secret_client_error(self, mock_boto_client):
        mock_boto_client.return_value.get_secret_value.side_effect = ClientError(
            {"Error": {"Code": "ResourceNotFoundException", "Message": "The requested secret was not found"}},
            "GetSecretValue"
        )

        with self.assertRaises(ClientError):
            get_secret()

    # Prueba: get_connection con respuesta correcta
    @patch("watched.watched_movie.utils.get_secret")
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
    @patch("watched.watched_movie.utils.get_secret")
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

    # Prueba: is_valid_user con respuesta correcta
    @patch("watched.watched_movie.app.get_connection")
    def test_is_valid_user(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.is_valid_user(1)
        self.assertTrue(result)

    # Prueba: is_valid_user con usuario no válido
    @patch("watched.watched_movie.app.get_connection")
    def test_is_valid_user_invalid(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = None
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.is_valid_user(1)
        self.assertFalse(result)

    # Prueba: is_active_movie con respuesta correcta
    @patch("watched.watched_movie.app.get_connection")
    def test_is_active_movie(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = (1,)
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.is_active_movie(1)
        self.assertTrue(result)

    # Prueba: is_active_movie con película no activa
    @patch("watched.watched_movie.app.get_connection")
    def test_is_active_movie_inactive(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.return_value = None
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        result = app.is_active_movie(1)
        self.assertFalse(result)

    # Prueba: mark_movie_as_watched con respuesta correcta (inserción)
    # @patch("watched.watched_movie.app.get_connection")
    # def test_mark_movie_as_watched_insert(self, mock_get_connection):
    #     mock_connection = MagicMock()
    #     mock_cursor = MagicMock()
    #     mock_cursor.fetchone.return_value = None
    #     mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
    #     mock_get_connection.return_value = mock_connection
    #
    #     result = app.mark_movie_as_watched(1, 2)
    #     self.assertFalse(result)
    #     mock_cursor.execute.assert_called_with(
    #         """
    #         INSERT INTO WatchedMovies (user_id, movie_id, status)
    #         VALUES (%s, %s, 1)
    #         """,
    #         (1, 2)
    #     )

    # Prueba: mark_movie_as_watched con respuesta correcta (actualización)
    # @patch("watched.watched_movie.app.get_connection")
    # def test_mark_movie_as_watched_update(self, mock_get_connection):
    #     mock_connection = MagicMock()
    #     mock_cursor = MagicMock()
    #     mock_cursor.fetchone.return_value = (1,)
    #     mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
    #     mock_get_connection.return_value = mock_connection
    #
    #     result = app.mark_movie_as_watched(1, 2)
    #     self.assertTrue(result)
    #     mock_cursor.execute.assert_called_with(
    #         """
    #         UPDATE WatchedMovies
    #         SET status = 0
    #         WHERE user_id = %s AND movie_id = %s
    #         """,
    #         (1, 2)
    #     )

    # Prueba: mark_movie_as_watched con error de consulta
    @patch("watched.watched_movie.app.get_connection")
    def test_mark_movie_as_watched_query_error(self, mock_get_connection):
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_cursor.fetchone.side_effect = Exception("Query execution error")
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_get_connection.return_value = mock_connection

        with self.assertRaises(Exception) as context:
            app.mark_movie_as_watched(1, 2)
        self.assertEqual(str(context.exception), "Query execution error")


if __name__ == '__main__':
    unittest.main()
