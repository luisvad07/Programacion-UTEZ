import unittest
from unittest.mock import patch, MagicMock
import json
from movies.change_status_movie.app import lambda_handler, toggle_movie_status
from movies.change_status_movie.utils import get_connection


class TestApp(unittest.TestCase):

    @patch('movies.change_status_movie.app.get_connection')
    def test_lambda_handler_success(self, mock_get_connection):
        # Arrange
        mock_get_connection.return_value.cursor.return_value.__enter__.return_value.fetchall.return_value = [
            (1, 'Title', 'Description', 'Genre', 'Image')
        ]
        event = {
            'pathParameters': {
                'id': '1'
            }
        }
        context = {}

        # Act
        response = lambda_handler(event, context)

        # Assert
        self.assertEqual(response['statusCode'], 200)
        self.assertEqual(response['headers'], {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "OPTIONS, POST, GET, PATCH, PUT",
            "Access-Control-Allow-Headers": "Content-Type"
        })
        body = json.loads(response['body'])
        self.assertIn('Película habilitada correctamente', body['message'])

    @patch('movies.change_status_movie.app.get_connection')
    def test_lambda_handler_no_id(self, mock_get_connection):
        # Arrange
        event = {
            'pathParameters': {}
        }
        context = {}

        # Act
        response = lambda_handler(event, context)

        # Assert
        self.assertEqual(response['statusCode'], 400)
        self.assertEqual(response['headers'], {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "OPTIONS, POST, GET, PATCH, PUT",
            "Access-Control-Allow-Headers": "Content-Type"
        })
        body = json.loads(response['body'])
        self.assertEqual(body['message'], 'Falta el ID de la película')

    @patch('movies.change_status_movie.app.get_connection')
    def test_lambda_handler_exception(self, mock_get_connection):
        # Arrange
        event = {
            'pathParameters': {
                'id': '1'
            }
        }
        context = {}
        mock_get_connection.side_effect = Exception("DB Error")

        # Act
        response = lambda_handler(event, context)

        # Assert
        self.assertEqual(response['statusCode'], 500)
        self.assertEqual(response['headers'], {
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Methods": "OPTIONS, POST, GET, PATCH, PUT",
            "Access-Control-Allow-Headers": "Content-Type"
        })
        body = json.loads(response['body'])
        self.assertEqual(body['message'], 'Error al actualizar el estado de la película en la base de datos')
        self.assertIn('DB Error', body['error'])

    @patch('movies.change_status_movie.app.get_connection')
    def test_toggle_movie_status_success(self, mock_get_connection):
        # Arrange
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_get_connection.return_value = mock_connection
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_cursor.fetchone.return_value = (1,)
        event = '1'

        # Act
        message = toggle_movie_status(event)

        # Assert
        self.assertEqual(message, "Película deshabilitada correctamente")

        # Normalize the SQL query to compare with actual call
        expected_query = "UPDATE Movies\n                SET status = %s\n                WHERE id = %s"
        actual_query = mock_cursor.execute.call_args[0][0].strip()

        self.assertEqual(actual_query, expected_query)
        self.assertEqual(mock_cursor.execute.call_args[0][1], (0, '1'))

    @patch('movies.change_status_movie.app.get_connection')
    def test_toggle_movie_status_no_movie(self, mock_get_connection):
        # Arrange
        mock_connection = MagicMock()
        mock_cursor = MagicMock()
        mock_get_connection.return_value = mock_connection
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
        mock_cursor.fetchone.return_value = None
        event = '1'

        # Act & Assert
        with self.assertRaises(Exception) as context:
            toggle_movie_status(event)
        self.assertEqual(str(context.exception), "La película no existe")

    @patch('movies.change_status_movie.utils.get_secret')
    @patch('movies.change_status_movie.utils.PyMySQL.connect')
    def test_get_connection_success(self, mock_connect, mock_get_secret):
        # Arrange
        mock_get_secret.return_value = {
            "host": "localhost",
            "username": "user",
            "password": "password",
            "dbInstanceIdentifier": "db",
            "port": 3306
        }
        mock_connect.return_value = MagicMock()

        # Act
        connection = get_connection()

        # Assert
        self.assertIsNotNone(connection)
        mock_connect.assert_called_with(
            host="localhost",
            user="user",
            password="password",
            database="db",
            port=3306
        )

    @patch('movies.change_status_movie.utils.get_secret')
    @patch('movies.change_status_movie.utils.PyMySQL.connect')
    def test_get_connection_secret_error(self, mock_connect, mock_get_secret):
        # Arrange
        mock_get_secret.side_effect = Exception("Secret Error")

        # Act & Assert
        with self.assertRaises(Exception) as context:
            get_connection()
        self.assertEqual(str(context.exception), "Secret Error")



if __name__ == '__main__':
    unittest.main()
