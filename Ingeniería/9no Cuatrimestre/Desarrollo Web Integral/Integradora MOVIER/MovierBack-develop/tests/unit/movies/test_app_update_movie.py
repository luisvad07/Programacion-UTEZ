import json
import unittest
from unittest.mock import patch, Mock
import pymysql
from movies.update_movie import app

mock_body = {
    "pathParameters": {
        "id": "1"
    },
    "body": json.dumps({
        "title": "Test1",
        "description": "Test1",
        "genre": "Comedia",
        "image": "dsad",
    })
}


class TestLambdaHandler(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.update_movie.app.title_exists")
    @patch("movies.update_movie.app.update_movie")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_update_movie, mock_title_exists):
        mock_connect.return_value = Mock()
        mock_title_exists.return_value = False
        mock_update_movie.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Película actualizada correctamente")

        mock_title_exists.assert_called_once_with("Test1", "1")
        mock_update_movie.assert_called_once_with("1", "Test1", "Test1", "Comedia", "dsad", None)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_id(self):
        mock_body = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener el ID de la película")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.update_movie.app.title_exists")
    def test_lambda_handler_movie_already_exists(self, mock_title_exists):
        mock_title_exists.return_value = True
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "La película con el mismo título ya existe")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_fields(self):
        mock_body_with_missing_fields = {
            "pathParameters": {
                "id": "1"
            },
            "body": json.dumps({
                "title": "",
                "description": "",
                "genre": "",
                "image": ""
            })
        }
        result = app.lambda_handler(mock_body_with_missing_fields, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Faltan campos a actualizar")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_long_fields(self):
        long_string = "a" * 256
        mock_body_with_long_fields = {
            "pathParameters": {
                "id": "1"
            },
            "body": json.dumps({
                "title": long_string,
                "description": "desc",
                "genre": "genre",
                "image": "image"
            })
        }
        result = app.lambda_handler(mock_body_with_long_fields, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El título no debe exceder los 255 caracteres")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.update_movie.app.update_movie")
    @patch("pymysql.connect")
    def test_lambda_handler_update_db_error(self, mock_connect, mock_update_movie):
        mock_connect.return_value = Mock()
        mock_update_movie.side_effect = Exception("Error al actualizar la película en la base de datos")

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al actualizar la película en la base de datos")


