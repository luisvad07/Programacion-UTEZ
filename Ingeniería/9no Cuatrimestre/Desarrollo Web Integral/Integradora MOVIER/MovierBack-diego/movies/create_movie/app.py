import json
import pymysql
from movies.create_movie.utils import get_connection

headers_open = {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "OPTIONS, POST, GET",
    "Access-Control-Allow-Headers": "Content-Type"
}


def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])
        title = body.get('title')
        description = body.get('description')
        genre = body.get('genre')
        image = body.get('image')
        status = body.get('status', 1)
    except Exception as e:
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps(
                {'message': 'Error al obtener los parámetros del cuerpo de la solicitud', 'error': str(e)})
        }

    if not all([title, description, genre, image]):
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps({'message': 'Faltan parámetros'})
        }

    if not all(isinstance(x, str) and len(x) <= 255 for x in [title, description, genre, image]):
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps({'message': 'Los parámetros deben ser cadenas de texto de máximo 255 caracteres'})
        }

    if not isinstance(status, int):
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps({'message': 'El parámetro status debe ser un número entero'})
        }

    if not (0 <= status <= 1):
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({'message': 'El parámetro status debe ser 0 o 1'})
        }

    try:
        if movie_exists(title):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'La película con el mismo título ya existe'})
            }
        insert_into_movies(title, description, genre, image, status)
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,

            'body': json.dumps({'message': 'Error al insertar en la base de datos', 'error': str(e)})
        }

    return {
        'statusCode': 200,
        'headers': headers_open,

        'body': json.dumps({'message': 'Película insertada correctamente'})
    }


def movie_exists(title):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            check_query = "SELECT COUNT(*) FROM Movies WHERE LOWER(title) = LOWER(%s)"
            cursor.execute(check_query, (title,))
            result = cursor.fetchone()
            return result[0] > 0
    finally:
        connection.close()


def insert_into_movies(title, description, genre, image, status):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            insert_query = """
                 INSERT INTO Movies (title, description, genre, image, status) VALUES (%s, %s, %s, %s, %s)
             """
            cursor.execute(insert_query, (title, description, genre, image, status))
            connection.commit()
    finally:
        connection.close()
