import json
from utils import get_connection


headers_open = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    }
def lambda_handler(event, context):
    try:
        user_id = event['pathParameters'].get('id')
    except Exception as e:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al obtener el ID de usuario de la solicitud', 'error': str(e)})
        }

    if user_id is None:
        return {
            'statusCode': 400,
            'headers': headers_open,
            'body': json.dumps({'message': 'Falta el ID de usuario en la solicitud'})
        }

    try:
        movies_watched = get_movies_watched_user(user_id)
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al obtener las películas vistas del usuario', 'error': str(e)})
        }

    return {
        'statusCode': 200,
        'headers': headers_open,
        'body': json.dumps(movies_watched)
    }

def get_movies_watched_user(user_id):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            query = """
                SELECT m.id, m.title, m.description, m.genre, m.image
                FROM WatchedMovies wm
                JOIN Movies m ON wm.movie_id = m.id
                WHERE wm.user_id = %s AND wm.status = 1 AND m.status = 1
            """
            cursor.execute(query, (user_id,))
            result = cursor.fetchall()

            movies_watched = []
            for row in result:
                movie = {
                    'movie_id': row[0],
                    'title': row[1],
                    'description': row[2],
                    'genre': row[3],
                    'image': row[4]
                }
                movies_watched.append(movie)

            return movies_watched

    finally:
        connection.close()
