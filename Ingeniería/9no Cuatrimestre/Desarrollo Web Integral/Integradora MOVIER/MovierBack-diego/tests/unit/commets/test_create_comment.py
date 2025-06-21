from unittest.mock import patch, MagicMock
import unittest
import json

from comments.create_comment import app
from comments.create_comment.utils import get_secret, get_connection

class TestApp(unittest.TestCase):

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("boto3.client")
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("comments.create_comment.app.insert_into_comments")
    @patch("pymysql.connect")
    def test_lambda_handler(self, mock_connect, mock_insert_comment, mock_user_exists, mock_movie_exists, mock_boto3_client):
        # Validación positiva
        mock_connect.return_value = MagicMock()
        mock_boto3_client.return_value = MagicMock()
        mock_user_exists.return_value = True
        mock_movie_exists.return_value = True
        mock_insert_comment.return_value = None

        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }

        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Comentario insertado correctamente")

    # Prueba: Falta de parámetros
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_missing_parameters(self):
        mock_body = {"body": json.dumps({})}
        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Faltan parámetros")

    # Prueba: user_id no es positivo
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_user_id_not_positive(self):
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
        self.assertEqual(body["message"], "El ID del usuario debe ser un entero positivo")

    # Prueba: movie_id no es positivo
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_movie_id_not_positive(self):
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
        self.assertEqual(body["message"], "El ID de la película debe ser un entero positivo")

    # Prueba: comentario vacío
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_comment_empty(self):
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
        self.assertEqual(body["message"], "El comentario no puede estar vacío")

    # Prueba: comentario demasiado largo
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    def test_lambda_handler_comment_too_long(self):
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
        self.assertEqual(body["message"], "El comentario no puede exceder los 255 caracteres")

    # Prueba: error al insertar el comentario
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("comments.create_comment.app.insert_into_comments")
    @patch("pymysql.connect")
    def test_lambda_handler_insert_comment_error(self, mock_connect, mock_insert_comment, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = MagicMock()
        mock_user_exists.return_value = True
        mock_movie_exists.return_value = True
        mock_insert_comment.side_effect = Exception("Database insert error")

        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }

        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Error al insertar el comentario en la base de datos")

    # Prueba: user_exists retorna falso
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("pymysql.connect")
    def test_lambda_handler_user_not_exists(self, mock_connect, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = MagicMock()
        mock_user_exists.return_value = False
        mock_movie_exists.return_value = True

        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }

        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "La película no existe")

    # Prueba: movie_exists retorna falso
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("comments.create_comment.app.user_exists")
    @patch("comments.create_comment.app.movie_exists")
    @patch("pymysql.connect")
    def test_lambda_handler_user_not_exists(self, mock_connect, mock_user_exists, mock_movie_exists):
        mock_connect.return_value = MagicMock()
        mock_user_exists.return_value = True
        mock_movie_exists.return_value = False

        mock_body = {
            "body": json.dumps({
                "user_id": 1,
                "movie_id": 1,
                "comment": "Me encantó la pelicula de principio a fin"
            })
        }

        result = app.lambda_handler(mock_body, None)
        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "El usuario no existe")

    # Prueba: error en la conexión a la base de datos
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("pymysql.connect")
    def test_lambda_handler_err_conexion(self, mock_connect):
        mock_connect.side_effect = Exception("Expecting value: line 1 column 1")

        mock_body = {"body": "Expecting value: line 1 column 1 (char 0)"}
        result = app.lambda_handler(mock_body, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertEqual(body["message"], "Error al obtener los parámetros del cuerpo de la solicitud")
        self.assertEqual(body["error"], "Expecting value: line 1 column 1 (char 0)")

    @patch("boto3.session.Session.client")
    def test_get_secret(self, mock_boto_client):
        # Simula la respuesta de AWS Secrets Manager
        mock_secret_string = json.dumps({
            "host": "localhost",
            "username": "test_user",
            "password": "test_pass",
            "dbInstanceIdentifier": "test_db",
            "port": 3306
        })

        # Mock del cliente de secretsmanager
        mock_client = MagicMock()
        mock_client.get_secret_value.return_value = {
            'SecretString': mock_secret_string
        }
        mock_boto_client.return_value = mock_client

        # Verifica que la función `get_secret` devuelva el diccionario correcto
        result = get_secret()
        self.assertEqual(result['host'], "localhost")
        self.assertEqual(result['username'], "test_user")
        self.assertEqual(result['password'], "test_pass")
        self.assertEqual(result['dbInstanceIdentifier'], "test_db")
        self.assertEqual(result['port'], 3306)

    @patch("comments.create_comment.utils.get_secret")
    @patch("pymysql.connect")
    def test_get_connection(self, mock_connect, mock_get_secret):
        # Simula el retorno de la función `get_secret`
        mock_get_secret.return_value = {
            "host": "localhost",
            "username": "test_user",
            "password": "test_pass",
            "dbInstanceIdentifier": "test_db",
            "port": 3306
        }

        # Simula la conexión a la base de datos
        mock_connect.return_value = MagicMock()

        # Llama a la función
        connection = get_connection()

        # Verifica que se haya llamado correctamente a `pymysql.connect` con los parámetros adecuados
        mock_connect.assert_called_with(
            host="localhost",
            user="test_user",
            password="test_pass",
            database="test_db",
            port=3306
        )

        # Verifica que la conexión se haya establecido
        self.assertIsNotNone(connection)

    @patch("comments.create_comment.app.get_connection")
    def test_user_exists(self, mock_get_connection):
        # Simula el objeto de conexión y el cursor
        mock_connection = MagicMock()
        mock_cursor = MagicMock()

        # Simula la respuesta del cursor
        mock_cursor.fetchone.return_value = [1]
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Configura el mock de `get_connection`
        mock_get_connection.return_value = mock_connection

        # Llama a la función y verifica el resultado
        result = app.user_exists(1)
        self.assertTrue(result)

        # Verifica que se haya llamado `execute` con la consulta y los parámetros correctos
        mock_cursor.execute.assert_called_once_with("SELECT COUNT(*) FROM Users WHERE id = %s", (1,))

        # Verifica que se haya cerrado la conexión
        mock_connection.close.assert_called_once()

    @patch("comments.create_comment.app.get_connection")
    def test_movie_exists(self, mock_get_connection):
        # Simula el objeto de conexión y el cursor
        mock_connection = MagicMock()
        mock_cursor = MagicMock()

        # Simula la respuesta del cursor
        mock_cursor.fetchone.return_value = [1]
        mock_connection.cursor.return_value.__enter__.return_value = mock_cursor

        # Configura el mock de `get_connection`
        mock_get_connection.return_value = mock_connection

        # Llama a la función y verifica el resultado
        result = app.movie_exists(1)
        self.assertTrue(result)

        # Verifica que se haya llamado `execute` con la consulta y los parámetros correctos
        mock_cursor.execute.assert_called_once_with("SELECT COUNT(*) FROM Movies WHERE id = %s", (1,))

        # Verifica que se haya cerrado la conexión
        mock_connection.close.assert_called_once()

    # @patch("comments.create_comment.app.get_connection")
    # def test_insert_into_comments(self, mock_get_connection):
    #     # Simula el objeto de conexión y el cursor
    #     mock_connection = MagicMock()
    #     mock_cursor = MagicMock()
    #
    #     # Configura el cursor para el contexto 'with'
    #     mock_connection.cursor.return_value.__enter__.return_value = mock_cursor
    #
    #     # Configura el mock de `get_connection`
    #     mock_get_connection.return_value = mock_connection
    #
    #     # Llama a la función
    #     app.insert_into_comments(1, 1, "Great movie!")
    #
    #     # Verifica que `execute` se haya llamado con la consulta y los parámetros correctos
    #     mock_cursor.execute.assert_called_once_with(
    #         "INSERT INTO Comments (user_id, movie_id, comment, date) VALUES (%s, %s, %s, current_timestamp())",
    #         (1, 1, "Great movie!")
    #     )
    #
    #     # Verifica que se haya llamado a `commit`
    #     mock_connection.commit.assert_called_once()
    #
    #     # Verifica que se haya cerrado la conexión
    #     mock_connection.close.assert_called_once()

    if __name__ == '__main__':
        unittest.main()