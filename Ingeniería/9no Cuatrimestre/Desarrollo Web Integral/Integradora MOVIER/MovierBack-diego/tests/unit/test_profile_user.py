import unittest
from unittest.mock import patch, MagicMock
import json
from login.profile_user import app


class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    def test_lambda_handler_success(self):
        mock_event = {
            "body": json.dumps({"user_id": "12345", "name": "John Doe"})
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], "Profile User access successful")

    # Prueba: cuerpo mal formado
    def test_lambda_handler_malformed_body(self):
        mock_event = {
            "body": "{user_id: 12345, name: John Doe}"  # JSON inválido
        }

        with self.assertRaises(json.JSONDecodeError):
            app.lambda_handler(mock_event, None)

    # Prueba: cuerpo vacío en el JSON
    def test_lambda_handler_empty_json(self):
        mock_event = {
            "body": "{}"
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], "Profile User access successful")


if __name__ == '__main__':
    unittest.main()
