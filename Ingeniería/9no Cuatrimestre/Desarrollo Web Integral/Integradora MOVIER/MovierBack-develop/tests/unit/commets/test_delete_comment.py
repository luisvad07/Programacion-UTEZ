from unittest.mock import patch, Mock
import unittest
import json

from comments.delete_comment import app

mock_body = {
    "body": json.dumps({
        "user_id": 1,
        "comment_id": 1
    })
}


class TestApp(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("comments.delete_comment.app.delete_comment")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_delete_comment, mock_get_comment_with_id):
        mock_connect.return_value = Mock()
        mock_get_comment_with_id.return_value = {'comment_id': 1, 'user_id': 1}
        mock_delete_comment.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Comentario eliminado exitosamente")

        mock_get_comment_with_id.assert_called_once_with(1)
        mock_delete_comment.assert_called_once_with(1)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_parameters(self):
        mock_body_without_parameters = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body_without_parameters, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el parámetro comment_id")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_user_parameter(self):
        mock_body_without_user_parameters = {"body": json.dumps({
            "comment_id": 1
        })}
        result = app.lambda_handler(mock_body_without_user_parameters, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el parámetro user_id")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_database_error(self, mock_connect):
        mock_connect.side_effect = Exception("Error al procesar la solicitud")

        mock_body = {"body": json.dumps({"comment_id": 1, "user_id": 1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al procesar la solicitud")
        self.assertIn("error", body)
        self.assertEqual(body["error"], "Error al procesar la solicitud")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("comments.delete_comment.app.delete_comment")
    @patch("pymysql.connect")
    def test_lambda_handler_comment_not_found(self, mock_connect, mock_delete_comment, mock_get_comment_with_id):
        mock_connect.return_value = Mock()
        mock_get_comment_with_id.return_value = None
        mock_delete_comment.return_value = None

        mock_body_wrong_comment_id = {
            "body": json.dumps({
                "user_id": 1,
                "comment_id": 150
            })
        }
        result = app.lambda_handler(mock_body_wrong_comment_id, None)
        self.assertEqual(result['statusCode'], 404)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Comentario no encontrado")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_error_getting_parameters(self, mock_connect):
        mock_connect.return_value = Mock()

        mock_body = {"body": "invalid_body"}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener los parámetros del cuerpo de la solicitud")
        self.assertIn("error", body)

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_comment_id_not_integer(self, mock_connect):
        mock_connect.return_value = Mock()

        mock_body = {"body": json.dumps({"comment_id": -1, "user_id": 1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro comment_id debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_user_id_not_integer(self, mock_connect):
        mock_connect.return_value = Mock()

        mock_body = {"body": json.dumps({"comment_id": 1, "user_id": -1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro user_id debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("pymysql.connect")
    def test_lambda_handler_user_not_authorized(self, mock_connect, mock_get_comment_with_id):
        mock_connect.return_value = Mock()
        mock_get_comment_with_id.return_value = {'comment_id': 1, 'user_id': 2}

        mock_body = {"body": json.dumps({"comment_id": 1, "user_id": 1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 403)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Usuario no autorizado para eliminar este comentario")


