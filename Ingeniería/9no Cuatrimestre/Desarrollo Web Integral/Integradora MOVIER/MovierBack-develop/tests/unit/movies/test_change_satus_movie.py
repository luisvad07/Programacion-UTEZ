from unittest.mock import patch, Mock
import unittest
import json
from movies.change_status_movie import app

mock_event = {
    "pathParameters": {
        "id": "1"
    }
}


class TestApp(unittest.TestCase):
    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.change_status_movie.app.pymysql.connect")
    def test_lambda_handler(self, mock_connect):
        mock_connection = Mock()
        mock_cursor = Mock()
        mock_connect.return_value = mock_connection
        mock_connection.cursor.return_value = mock_cursor

        mock_cursor.fetchone.return_value = (1,)

        result = app.lambda_handler(mock_event, None)

        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Película deshabilitada correctamente")

        mock_cursor.execute.assert_any_call("SELECT status FROM Movies WHERE id = %s", ('1',))
        mock_cursor.execute.assert_any_call("UPDATE Movies SET status = %s WHERE id = %s", (0, '1'))

        mock_connection.commit.assert_called_once()

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.change_status_movie.app.pymysql.connect")
    def test_lambda_handler_movie_not_found(self, mock_connect):
        mock_connection = Mock()
        mock_cursor = Mock()
        mock_connect.return_value = mock_connection
        mock_connection.cursor.return_value = mock_cursor

        mock_cursor.fetchone.return_value = None

        result = app.lambda_handler(mock_event, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al actualizar el estado de la película en la base de datos")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.change_status_movie.app.pymysql.connect")
    def test_lambda_handler_missing_id(self, mock_connect):
        mock_event_missing_id = {
            "pathParameters": {}
        }

        result = app.lambda_handler(mock_event_missing_id, None)

        self.assertEqual(result['statusCode'], 400)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Falta el ID de la película")

    @patch.dict("os.environ", {"REGION_NAME": "us-east-2", "DATA_BASE": "movier-test"})
    @patch("movies.change_status_movie.app.pymysql.connect")
    def test_lambda_handler_db_error(self, mock_connect):
        mock_connect.side_effect = Exception("Error al actualizar el estado de la película en la base de datos")

        result = app.lambda_handler(mock_event, None)

        self.assertEqual(result['statusCode'], 500)
        body = json.loads(result['body'])
        self.assertIn("message", body)
        self.assertEqual(body["message"], "Error al actualizar el estado de la película en la base de datos")


