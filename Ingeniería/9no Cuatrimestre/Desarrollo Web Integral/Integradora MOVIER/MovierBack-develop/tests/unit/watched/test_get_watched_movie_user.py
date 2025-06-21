from unittest.mock import patch, Mock
import unittest
import json

from watched.get_watched_movies_user.app import lambda_handler

mock_path = {
    "pathParameters": {
        "id": "1"
    }
}

class TestLambdaHandler(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_missing_id(self, mock_connect):
        mock_path_missing_id = {
            "pathParameters": {}
        }

        result = lambda_handler(mock_path_missing_id, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el ID de usuario en la solicitud")


    #test for Error al obtener las películas vistas del usuario
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_error_get_movies(self, mock_connect):
        mock_connect.return_value.cursor.return_value.__enter__.return_value.fetchall.side_effect = Exception("Error")

        result = lambda_handler(mock_path, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener las películas vistas del usuario")
        self.assertIn("error", body)
        self.assertEqual(body["error"], "Error")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_successful(self, mock_connect):
        mock_cursor = mock_connect.return_value.cursor.return_value
        mock_cursor.fetchall.return_value = [
            (1, "Example Movie", "An example movie", "Action", "example.jpg")
        ]

        result = lambda_handler(mock_path, None)

        body = json.loads(result['body'])

        self.assertEqual(result['statusCode'], 200)
        self.assertIsInstance(body, list)
        self.assertEqual(len(body), 1)
        movie = body[0]
        self.assertEqual(movie["movie_id"], 1)
        self.assertEqual(movie["title"], "Example Movie")
        self.assertEqual(movie["description"], "An example movie")
        self.assertEqual(movie["genre"], "Action")
        self.assertEqual(movie["image"], "example.jpg")