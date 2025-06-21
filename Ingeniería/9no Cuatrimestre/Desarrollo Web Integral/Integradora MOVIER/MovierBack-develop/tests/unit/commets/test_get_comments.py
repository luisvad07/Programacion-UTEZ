from unittest.mock import patch, Mock
import unittest
import json

from comments.get_comments import app

mock_path = {
    "pathParameters": {
        "id": 1
    }
}


class TestApp(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_movie_exists, mock_get_comments_with_movie_id):
        mock_connect.return_value = Mock()
        mock_movie_exists.return_value = True
        mock_get_comments_with_movie_id.return_value = [
            {"comment_id": 1, "user_id": 1, "movie_id": 1, "comment": "Me encantó la película de principio a fin", "date": "2024-07-17 15:30:00"}]

        result = app.lambda_handler(mock_path, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("Comentarios", body)
        self.assertEqual(len(body["Comentarios"]), 1)
        self.assertEqual(body["Comentarios"][0]["comment_id"], 1)
        self.assertEqual(body["Comentarios"][0]["user_id"], 1)
        self.assertEqual(body["Comentarios"][0]["movie_id"], 1)
        self.assertEqual(body["Comentarios"][0]["comment"], "Me encantó la película de principio a fin")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_parameters(self):
        mock_body = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener los parámetros del cuerpo de la solicitud")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.get_comments.app.movie_exists")
    def test_lambda_handler_movie_not_found(self, mock_movie_exists):
        mock_movie_exists.return_value = False
        result = app.lambda_handler(mock_path, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "La película no existe")


    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.get_comments.app.movie_exists")
    def test_lambda_handler_movie_id_not_integer(self, mock_movie_exists):
        mock_path = {
            "pathParameters": {
                "id": "-1"
            }
        }
        result = app.lambda_handler(mock_path, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro movie_id debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.get_comments.app.movie_exists")
    def test_lambda_handler_movie_missing_id(self, mock_movie_exists):
        mock_path = {
            "pathParameters": {
                "id": ""
            }
        }
        result = app.lambda_handler(mock_path, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el parámetro movie_id")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.get_comments.app.get_comments_with_movie_id")
    @patch("comments.get_comments.app.movie_exists")
    @patch("pymysql.connect")
    def test_lambda_handler_error_getting_comments(self, mock_connect, mock_movie_exists, mock_get_comments_with_movie_id):
        mock_connect.return_value = Mock()
        mock_movie_exists.return_value = True
        mock_get_comments_with_movie_id.side_effect = Exception("Error al obtener los comentarios de la base de datos")
        result = app.lambda_handler(mock_path, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener los comentarios de la base de datos")



