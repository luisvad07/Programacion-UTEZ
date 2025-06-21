import json
from utils import get_connection

headers_open = {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': '*',
    'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
}

def lambda_handler(event, context):
    try:
        movie_id = event['pathParameters'].get('id')
        user_id = event['pathParameters'].get('user_id')
        if not movie_id:
            raise ValueError("No se proporcionó el ID de la película")
        if not user_id:
            raise ValueError("No se proporcionó el ID del usuario")

        status = 1
        movie = get_movie_by_id(status, movie_id, user_id)
        if not movie:
            return {
                'statusCode': 404,
                'headers': headers_open,
                'body': json.dumps({'message': 'Película no encontrada'})
            }

    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps({'message': 'Error al obtener la película de la base de datos', 'error': str(e)})
        }
#S
    return {
        'statusCode': 200,
        'headers': headers_open,
        'body': json.dumps({'Pelicula': movie})
    }

def get_movie_by_id(status, movie_id, user_id):
    connection = get_connection()
    movie = None

    try:
        with connection.cursor() as cursor:
            query = """
                SELECT id, title, description, genre, image, status,
                       (SELECT status FROM WatchedMovies WHERE WatchedMovies.movie_id = Movies.id AND WatchedMovies.user_id = %s) AS watched
                FROM Movies
                WHERE status = %s AND id = %s;
                """
            cursor.execute(query, (user_id, status, movie_id))
            result = cursor.fetchone()
            if result:
                movie = {
                    'id': result[0],
                    'title': result[1],
                    'description': result[2],
                    'genre': result[3],
                    'image': result[4],
                    'status': result[5],
                    'watched': result[6],
                }
    finally:
        connection.close()

    return movie
