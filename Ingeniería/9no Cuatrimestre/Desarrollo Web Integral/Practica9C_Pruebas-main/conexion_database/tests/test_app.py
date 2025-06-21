import unittest
from unittest.mock import patch, MagicMock
import app

class TestLambdaHandler(unittest.TestCase):

    @patch('app.get_db_connection')
    def test_lambda_handler_db_error(self, mock_get_db_connection):
        # Configurar el mock para lanzar una excepción de MySQL
        mock_get_db_connection.side_effect = app.pymysql.MySQLError("Database connection failed")

        # Llamar al lambda_handler
        result = app.lambda_handler({}, {})

        # Verificar el resultado
        self.assertEqual(result['statusCode'], 500)
        self.assertIn('Database connection failed', result['body']['error'])

    @patch('app.get_db_connection')
    def test_lambda_handler_general_exception(self, mock_get_db_connection):
        # Configurar el mock para lanzar una excepción general
        mock_get_db_connection.side_effect = Exception("An error occurred")

        # Llamar al lambda_handler
        result = app.lambda_handler({}, {})

        # Verificar el resultado
        self.assertEqual(result['statusCode'], 500)
        self.assertIn('An error occurred', result['body']['error'])

if __name__ == '__main__':
    unittest.main()
