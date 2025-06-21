import json
import pymysql
from movies.update_movie.utils import get_connection


headers_open = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    }

def lambda_handler(event, context):
    try:
        movie_id = event['pathParameters'].get('id')
    except Exception as e:
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps({'message': 'Error al obtener el ID de la película', 'error': str(e)})
        }

    if movie_id is None:
        return {
            'statusCode': 400,
            'headers': headers_open,

            'body': json.dumps({'message': 'Falta el ID de la película'})
        }

    try:
        request_body = json.loads(event['body'])
        title = request_body.get('title')
        description = request_body.get('description')
        genre = request_body.get('genre')
        image = request_body.get('image')
        status = request_body.get('status')

        # Validaciones
        if not any([title, description, genre, image]):
            return {
                'statusCode': 400,
                'headers': headers_open,

                'body': json.dumps({'message': 'Faltan campos a actualizar'})
            }

        if title and len(title) > 255:
            return {
                'statusCode': 400,
                'headers': headers_open,

                'body': json.dumps({'message': 'El título no debe exceder los 255 caracteres'})
            }

        if description and len(description) > 255:
            return {
                'statusCode': 400,
                'headers': headers_open,

                'body': json.dumps({'message': 'La descripción no debe exceder los 255 caracteres'})
            }

        if genre and len(genre) > 255:
            return {
                'statusCode': 400,
                'headers': headers_open,

                'body': json.dumps({'message': 'El género no debe exceder los 255 caracteres'})
            }

        if image and len(image) > 255:
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'La URL de la imagen no debe exceder los 255 caracteres'})
            }

        if status is not None:
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'No se permite actualizar el campo "status"'})
            }

        if title and title_exists(title, movie_id):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'La película con el mismo título ya existe'})
            }

        update_movie(movie_id, title, description, genre, image, status)
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al actualizar la película en la base de datos', 'error': str(e)})
        }

    return {
        'statusCode': 200,
        'headers': headers_open,
        'body': json.dumps({'message': 'Película actualizada correctamente'})
    }

def title_exists(title, movie_id):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            check_query = "SELECT COUNT(*) FROM Movies WHERE LOWER(title) = LOWER(%s) AND id != %s"
            cursor.execute(check_query, (title, movie_id))
            result = cursor.fetchone()
            return result[0] > 0
    finally:
        connection.close()

def update_movie(movie_id, title, description, genre, image, status):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            update_query = """
                UPDATE Movies
                SET title = %s, description = %s, genre = %s, image = %s
                WHERE id = %s
            """
            cursor.execute(update_query, (title, description, genre, image, movie_id))
            connection.commit()
    finally:
        connection.close()
