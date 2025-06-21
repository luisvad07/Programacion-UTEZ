from unittest.mock import patch, Mock
import unittest
import json
from movies.get_movies import app

mock_event = {
    "body": json.dumps({
        "title": "Test1",
        "description": "Test1",
        "genre": "Comedia",
        "image": "dsad",
        "status": 1
    })
}

class TestApp(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.get_movies.app.get_movies_with_status")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_get_movies_with_status):
        mock_connect.return_value = Mock()
        mock_get_movies_with_status.return_value = [
            {
                'id': 1,
                'title': 'Test1',
                'description': 'Test1',
                'genre': 'Comedia',
                'image': 'dsad',
                'status': 1
            }
        ]

        result = app.lambda_handler(mock_event, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("Peliculas", body)
        self.assertEqual(len(body["Peliculas"]), 1)
        self.assertEqual(body["Peliculas"][0]['title'], "Test1")

        mock_get_movies_with_status.assert_called_once_with(1)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.get_movies.app.get_movies_with_status")
    def test_lambda_handler_no_movies(self, mock_get_movies_with_status):
        mock_get_movies_with_status.return_value = []
        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("Peliculas", body)
        self.assertEqual(len(body["Peliculas"]), 0)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.get_movies.app.get_movies_with_status")
    @patch("pymysql.connect")
    def test_lambda_handler_db_error(self, mock_connect, mock_get_movies_with_status):
        mock_connect.return_value = Mock()
        mock_get_movies_with_status.side_effect = Exception("Database error")

        result = app.lambda_handler(mock_event, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener las películas de la base de datos")



