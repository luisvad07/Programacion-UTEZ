from login.set_password import (app)
import unittest
import json


mock_body = {
    "body": json.dumps({
        "username": "holisbebe9@gmail.com",
        "temporary_password": "Holisbebe9.",
        "new_password": "Joviicam123*"
    })
}


class TestApp(unittest.TestCase):
    def test_lambda_handler(self):
        result = app.lambda_handler(mock_body, None)
        print(result)
