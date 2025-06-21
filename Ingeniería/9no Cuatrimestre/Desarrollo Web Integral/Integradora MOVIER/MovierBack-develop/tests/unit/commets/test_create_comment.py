from unittest.mock import patch, Mock
import unittest
import json

from comments.create_comment import (app)

mock_body = {
    "body": json.dumps({
        "user_id": 1,
        "movie_id": 1,
        "comment": "Me encantó la pelicula de principio a fin"
    })
}


class TestApp(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("comments.create_comment.app.insert_into_comments")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_insert_comment, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = Mock()
        mock_user_exists.return_value = True
        mock_movie_exists.return_value = True
        mock_insert_comment.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Comentario insertado correctamente")

        mock_user_exists.assert_called_once_with(1)
        mock_movie_exists.assert_called_once_with(1)
        mock_insert_comment.assert_called_once_with(1, 1, "Me encantó la pelicula de principio a fin")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_parameters(self):
        mock_body = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Faltan parámetros")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_err_conexion(self, mock_connect):
        mock_connect.side_effect = Exception("Expecting value: line 1 column 1")

        body_mock = {
            "body": "Expecting value: line 1 column 1 (char 0)"
        }
        result = app.lambda_handler(body_mock, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener los parámetros del cuerpo de la solicitud")
        self.assertIn("error", body)
        self.assertEqual(body["error"], "Expecting value: line 1 column 1 (char 0)")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("comments.create_comment.app.insert_into_comments")
    @patch("pymysql.connect")
    def test_lambda_handler_movie_not_exists(self, mock_connect, mock_insert_comment, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = Mock()
        mock_user_exists.return_value = False
        mock_movie_exists.return_value = True
        mock_insert_comment.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "La película no existe")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("comments.create_comment.app.insert_into_comments")
    @patch("pymysql.connect")
    def test_lambda_handler_user_not_exists(self, mock_connect, mock_insert_comment, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = Mock()
        mock_user_exists.return_value = True
        mock_movie_exists.return_value = False
        mock_insert_comment.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El usuario no existe")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_user_id_not_positive(self, mock_connect):
        mock_body = {
            "body": json.dumps({
                "user_id": -1,
                "movie_id": 1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El ID del usuario debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_movie_id_not_positive(self, mock_connect):
        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": -1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El ID de la película debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_comment_empty(self, mock_connect):
        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": ""
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El comentario no puede estar vacío")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_comment_too_long(self, mock_connect):
        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": "a" * 1001
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El comentario no puede exceder los 1000 caracteres")


