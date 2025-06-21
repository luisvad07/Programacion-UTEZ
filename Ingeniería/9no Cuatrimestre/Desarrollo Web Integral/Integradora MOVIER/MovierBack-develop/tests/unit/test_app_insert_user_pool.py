import json
import unittest
from login.insert_user_pool import app

mock_body = {
    "body": json.dumps({
        "email": "holisbebe9@utez.edu.mx",
        "user_name": "joviis",
        "phone_number": "+527351378169",
        "name": "Brenda Johana Gálvez Álvarez",
        "age": 21,
        "gender": "F"
    })
}


class TestApp(unittest.TestCase):
    def test_lambda_handler(self):
        result = app.lambda_handler(mock_body, None)
        print(result)
