from unittest.mock import patch, Mock
import unittest
import json
from movies.create_movie import (app)

mock_body = {
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
    @patch("movies.create_movie.app.movie_exists")
    @patch("movies.create_movie.app.insert_into_movies")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_insert_into_movies, mock_movie_exists):
        mock_connect.return_value = Mock()
        mock_movie_exists.return_value = False
        mock_insert_into_movies.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Película insertada correctamente")




        mock_movie_exists.assert_called_once_with("Test1")
        mock_insert_into_movies.assert_called_once_with("Test1", "Test1", "Comedia", "dsad", 1)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_parameters(self):
        mock_body = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Faltan parámetros")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.create_movie.app.movie_exists")
    def test_lambda_handler_movie_already_exists(self, mock_movie_exists):
        mock_movie_exists.return_value = True
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "La película con el mismo título ya existe")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_parameter_length_exceeded(self):
        mock_body = {
            "body": json.dumps({
                "title": "a" * 100056,
                "description": "Test1",
                "genre": "Comedia",
                "image": "dsad",
                "status": 1
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Los parámetros deben ser cadenas de texto de máximo 255 caracteres")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_invalid_status_type(self):
        mock_body = {
            "body": json.dumps({
                "title": "Test1",
                "description": "Test1",
                "genre": "Comedia",
                "image": "dsad",
                "status": "invalid"
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro status debe ser un número entero")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_status_out_of_range(self):
        mock_body = {
            "body": json.dumps({
                "title": "Test1",
                "description": "Test1",
                "genre": "Comedia",
                "image": "dsad",
                "status": 2
            })
        }
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro status debe ser 0 o 1")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.create_movie.app.insert_into_movies")
    @patch("pymysql.connect")
    def test_lamda_handler_500(self, mock_connect, __ ):
        mock_connect.side_effect = Exception("Error al insertar en la base de datos")
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al insertar en la base de datos")





