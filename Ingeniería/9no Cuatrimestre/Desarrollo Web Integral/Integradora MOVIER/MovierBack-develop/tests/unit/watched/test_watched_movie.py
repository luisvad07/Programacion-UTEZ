from unittest.mock import patch, Mock
import unittest
import json

from watched.watched_movie.app import lambda_handler, is_valid_user, is_active_movie, mark_movie_as_watched

class TestLambdaHandler(unittest.TestCase):

    @patch("watched.watched_movie.app.pymysql.connect")
    def test_lambda_handler_invalid_user_movie_ids(self, mock_connect):
        event = {
            "body": json.dumps({
                "user_id": "not_an_int",
                "movie_id": "not_an_int"
            })
        }

        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Los IDs de usuario y película deben ser enteros")



    @patch("watched.watched_movie.app.is_valid_user")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    def test_lambda_handler_valid_requests(self, mock_mark_movie_as_watched, mock_is_active_movie, mock_is_valid_user):
        # Test valid request
        event = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1
            })
        }

        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = True
        mock_mark_movie_as_watched.return_value = False

        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Película marcada como vista con éxito")

        # Test valid request when movie is unmarked
        mock_mark_movie_as_watched.return_value = True
        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Película desmarcada como vista")

    @patch("watched.watched_movie.app.is_valid_user")
    def test_lambda_handler_invalid_user(self, mock_is_valid_user):
        # Test invalid user
        event = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1
            })
        }

        mock_is_valid_user.return_value = False

        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "El usuario no existe o no es válido")

    @patch("watched.watched_movie.app.is_valid_user")
    @patch("watched.watched_movie.app.is_active_movie")
    def test_lambda_handler_inactive_movie(self, mock_is_active_movie, mock_is_valid_user):
        # Test inactive movie
        event = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1
            })
        }

        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = False

        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "La película no existe o no está activa")

    @patch("watched.watched_movie.app.is_valid_user")
    @patch("watched.watched_movie.app.is_active_movie")
    @patch("watched.watched_movie.app.mark_movie_as_watched")
    def test_lambda_handler_exception(self, mock_mark_movie_as_watched, mock_is_active_movie, mock_is_valid_user):
        # Test exception handling
        event = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1
            })
        }

        mock_is_valid_user.return_value = True
        mock_is_active_movie.return_value = True
        mock_mark_movie_as_watched.side_effect = Exception("Database error")

        result = lambda_handler(event, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Error al marcar la película como vista en la base de datos")
        self.assertEqual(body["error"], "Database error")

    @patch("watched.watched_movie.app.pymysql.connect")
    def test_is_valid_user(self, mock_connect):
        user_id = 1
        connection = mock_connect.return_value
        cursor = connection.cursor.return_value
        cursor.fetchone.return_value = True

        result = is_valid_user(user_id)
        self.assertTrue(result)

    @patch("watched.watched_movie.app.pymysql.connect")
    def test_is_active_movie(self, mock_connect):
        movie_id = 1
        connection = mock_connect.return_value
        cursor = connection.cursor.return_value
        cursor.fetchone.return_value = True

        result = is_active_movie(movie_id)
        self.assertTrue(result)

