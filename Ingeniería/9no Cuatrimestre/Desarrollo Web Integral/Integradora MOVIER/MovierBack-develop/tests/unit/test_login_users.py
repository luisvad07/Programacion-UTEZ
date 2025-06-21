import unittest
import json

from login.login_users import (app)

mock_body = {
    "body": json.dumps({
        "username": "holisbebe9@gmail.com",
        "password": "Joviicam123*"
    })
}


class TestApp(unittest.TestCase):
    def test_lambda_handler(self):
        result = app.lambda_handler(mock_body, None)
        print(result)
