from unittest.mock import patch, MagicMock
import unittest
import json

from login.profile_admin import app

class TestApp(unittest.TestCase):

    # Prueba: caso exitoso
    def test_lambda_handler_success(self):
        mock_event = {
            'body': json.dumps({'key': 'value'})
        }

        result = app.lambda_handler(mock_event, None)
        self.assertEqual(result['statusCode'], 200)
        body = json.loads(result['body'])
        self.assertEqual(body['message'], "User admin access successful")

    # Prueba: caso sin cuerpo en la solicitud
    # def test_lambda_handler_no_body(self):
    #     mock_event = {
    #         'body': ''
    #     }
    #
    #     result = app.lambda_handler(mock_event, None)
    #     self.assertEqual(result['statusCode'], 200)
    #     body = json.loads(result['body'])
    #     self.assertEqual(body['message'], "User admin access successful")

    # Prueba: caso con cuerpo malformado en la solicitud
    def test_lambda_handler_malformed_body(self):
        mock_event = {
            'body': '{"key": "value"'  # Cuerpo JSON malformado
        }

        with self.assertRaises(json.JSONDecodeError):
            app.lambda_handler(mock_event, None)

    # Prueba: caso con un evento vacío
    def test_lambda_handler_empty_event(self):
        mock_event = {}

        with self.assertRaises(KeyError):
            app.lambda_handler(mock_event, None)

    # Prueba: caso con cuerpo no JSON en la solicitud
    def test_lambda_handler_non_json_body(self):
        mock_event = {
            'body': 'This is not JSON'
        }

        with self.assertRaises(json.JSONDecodeError):
            app.lambda_handler(mock_event, None)

if __name__ == '__main__':
    unittest.main()
