import unittest
from unittest.mock import patch, MagicMock
import json
import pymysql
import boto3
import base64
from botocore.exceptions import ClientError
from comments.delete_comment import app
from comments.delete_comment.utils import get_secret, get_connection


# Mock de propiedades
class MockProperties:
    region = "us-east-1"
    db_name = "test_db"


props = MockProperties()


# Test para las funciones en app.py y utils.py
class TestApp(unittest.TestCase):

    @patch("comments.delete_comment.app.get_connection")
    @patch("pymysql.cursors.DictCursor")
    def test_get_comment_with_id(self, mock_dict_cursor, mock_get_connection):
        # Simula el objeto de conexión y el cursor
        mock_connection = MagicMock()
        mock_cursor = MagicMock()

        # Configura el cursor para el contexto 'with'
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Configura el mock de `get_connection`
        mock_get_connection.return_value = mock_connection

        # Configura el retorno del cursor
        mock_cursor.fetchone.return_value = {'id': 1, 'user_id': 1, 'comment': 'Test comment'}

        # Llama a la función
        comment = app.get_comment_with_id(1)

        # Verifica que el comentario se haya recuperado correctamente
        self.assertEqual(comment, {'id': 1, 'user_id': 1, 'comment': 'Test comment'})

        # Verifica que se haya llamado a `execute` con la consulta correcta
        mock_cursor.execute.assert_called_once_with("SELECT * FROM Comments WHERE id = %s", (1,))

        # Verifica que se haya cerrado la conexión
        mock_connection.close.assert_called_once()

    @patch("comments.delete_comment.app.get_connection")
    def test_delete_comment(self, mock_get_connection):
        # Simula el objeto de conexión y el cursor
        mock_connection = MagicMock()
        mock_cursor = MagicMock()

        # Configura el cursor para el contexto 'with'
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Configura el mock de `get_connection`
        mock_get_connection.return_value = mock_connection

        # Llama a la función
        app.delete_comment(1)

        # Verifica que `execute` se haya llamado con la consulta correcta
        mock_cursor.execute.assert_called_once_with("DELETE FROM Comments WHERE id = %s", (1,))

        # Verifica que se haya llamado a `commit`
        mock_connection.commit.assert_called_once()

        # Verifica que se haya cerrado la conexión
        mock_connection.close.assert_called_once()

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("comments.delete_comment.app.delete_comment")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_delete_comment, mock_get_comment_with_id):
        # Define el cuerpo del mock
        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "comment_id": 1
            })
        }

        mock_connect.return_value = MagicMock()
        mock_get_comment_with_id.return_value = {'comment_id': 1, 'user_id': 1}
        mock_delete_comment.return_value = None

        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Comentario eliminado exitosamente")

        mock_get_comment_with_id.assert_called_once_with(1)
        mock_delete_comment.assert_called_once_with(1)

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    def test_lambda_handler_missing_parameters(self):
        mock_body_without_parameters = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body_without_parameters, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el parámetro comment_id")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    def test_lambda_handler_missing_user_parameter(self):
        mock_body_without_user_parameters = {"body": json.dumps({"comment_id": 1})}
        result = app.lambda_handler(mock_body_without_user_parameters, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el parámetro user_id")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
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
        self.assertEqual(body["error"], "Unable to locate credentials")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("pymysql.connect")
    def test_lambda_handler_comment_not_found(self, mock_connect, mock_get_comment_with_id):
        mock_connect.return_value = MagicMock()
        mock_get_comment_with_id.return_value = None

        mock_body_wrong_comment_id = {"body": json.dumps({"user_id": 1, "comment_id": 150})}
        result = app.lambda_handler(mock_body_wrong_comment_id, None)
        self.assertEqual(result['statusCode'], 404)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Comentario no encontrado")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("pymysql.connect")
    def test_lambda_handler_error_getting_parameters(self, mock_connect):
        mock_connect.return_value = MagicMock()

        mock_body = {"body": "invalid_body"}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al obtener los parámetros del cuerpo de la solicitud")
        self.assertIn("error", body)

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("pymysql.connect")
    def test_lambda_handler_comment_id_not_integer(self, mock_connect):
        mock_connect.return_value = MagicMock()

        mock_body = {"body": json.dumps({"comment_id": -1, "user_id": 1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro comment_id debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("pymysql.connect")
    def test_lambda_handler_user_id_not_integer(self, mock_connect):
        mock_connect.return_value = MagicMock()

        mock_body = {"body": json.dumps({"comment_id": 1, "user_id": -1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "El parámetro user_id debe ser un entero positivo")

    @patch.dict("os.environ", {"REGION_NAME": props.region, "DATA_BASE": props.db_name})
    @patch("comments.delete_comment.app.get_comment_with_id")
    @patch("pymysql.connect")
    def test_lambda_handler_user_not_authorized(self, mock_connect, mock_get_comment_with_id):
        mock_connect.return_value = MagicMock()
        mock_get_comment_with_id.return_value = {'comment_id': 1, 'user_id': 2}

        mock_body = {"body": json.dumps({"comment_id": 1, "user_id": 1})}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 403)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Usuario no autorizado para eliminar este comentario")

    # @patch("comments.delete_comment.utils.boto3.client")
    # def test_get_secret(self, mock_boto_client):
    #     # Simula la respuesta de AWS Secrets Manager
    #     mock_client = MagicMock()
    #     mock_client.get_secret_value.return_value = {
    #         'SecretString': json.dumps({
    #             'host': 'localhost',
    #             'username': 'user',
    #             'password': 'password',
    #             'dbInstanceIdentifier': 'db',
    #             'port': 3306
    #         })
    #     }
    #     mock_boto_client.return_value = mock_client
    #
    #     # Llama a la función
    #     secret = get_secret()
    #
    #     # Verifica que los secretos se recuperen correctamente
    #     self.assertEqual(secret['host'], 'localhost')
    #     self.assertEqual(secret['username'], 'user')
    #     self.assertEqual(secret['password'], 'password')
    #     self.assertEqual(secret['dbInstanceIdentifier'], 'db')
    #     self.assertEqual(secret['port'], 3306)

    @patch("comments.delete_comment.utils.get_secret")
    @patch("pymysql.connect")
    def test_get_connection(self, mock_pymysql_connect, mock_get_secret):
        # Configura el retorno del mock de `get_secret`
        mock_get_secret.return_value = {
            'host': 'localhost',
            'username': 'user',
            'password': 'password',
            'dbInstanceIdentifier': 'db',
            'port': 3306
        }

        # Simula el objeto de conexión
        mock_connection = MagicMock()
        mock_pymysql_connect.return_value = mock_connection

        # Llama a la función
        connection = get_connection()

        # Verifica que `connect` se haya llamado con los parámetros correctos
        mock_pymysql_connect.assert_called_once_with(
            host='localhost',
            user='user',
            password='password',
            database='db',
            port=3306
        )

        # Verifica que se devuelva la conexión
        self.assertEqual(connection, mock_connection)


if __name__ == '__main__':
    unittest.main()
