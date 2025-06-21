import json
from utils import get_connection

headers_open = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': '*',
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
}


#3
def lambda_handler(event, context):
    try:
        movie = event['pathParameters'].get('id')
    except Exception as e:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps(
                {'message': 'Error al obtener los parámetros del cuerpo de la solicitud', 'error': str(e)})
        }
    if not movie:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({'message': 'Falta el parámetro movie_id'})
        }

    try:
        movie = int(movie)
        if movie <= 0:
            raise ValueError('El parámetro movie_id debe ser un entero positivo')
    except ValueError as e:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({'message': str(e)})
        }

    try:
        if not movie_exists(movie):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'La película no existe'})
            }
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al verificar la existencia de la película', 'error': str(e)})
        }

    try:
        movie = int(movie)
        comments = get_comments_with_movie_id(movie)
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al obtener los comentarios de la base de datos', 'error': str(e)})
        }

    return {
        'statusCode': 200,
        'headers': headers_open,
        'body': json.dumps({'Comentarios': comments})
    }


def movie_exists(movie_id):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            check_query = "SELECT COUNT(*) FROM Movies WHERE id = %s"
            cursor.execute(check_query, (movie_id,))
            result = cursor.fetchone()
            return result[0] > 0
    finally:
        connection.close()


def get_comments_with_movie_id(movie_id):
    connection = get_connection()
    comments = []

    try:
        with connection.cursor() as cursor:
            query = """
                SELECT c.id, c.user_id, c.movie_id, c.comment, c.date, u.username
                FROM Comments c
                INNER JOIN Users u ON c.user_id = u.id
                WHERE c.movie_id = %s
                """
            cursor.execute(query, (movie_id,))
            result = cursor.fetchall()
            for row in result:
                comment = {
                    'comment_id': row[0],
                    'user_id': row[1],
                    'movie_id': row[2],
                    'comment': row[3],
                    'date': row[4].strftime('%Y-%m-%d %H:%M:%S'),
                    'username': row[5]
                }
                comments.append(comment)
    finally:
        connection.close()

    return comments
