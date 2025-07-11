import json
import pymysql
from movies.get_movies.utils import get_connection


headers_open = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    }
def lambda_handler(event, context):
    try:
        movies = get_movies_with_status(1)
    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,

            'body': json.dumps({'message': 'Error al obtener las películas de la base de datos', 'error': str(e)})
        }

    return {
        'statusCode': 200,
        'headers': headers_open,
        'body': json.dumps({'Peliculas': movies})
    }


def get_movies_with_status(status):
    connection = get_connection()
    movies = []

    try:
        with connection.cursor() as cursor:
            query = """
                SELECT id,title, description, genre, image, status
                FROM Movies
                WHERE status = %s
                """
            cursor.execute(query, (status,))
            result = cursor.fetchall()
            for row in result:
                movie = {
                    'id': row[0],
                    'title': row[1],
                    'description': row[2],
                    'genre': row[3],
                    'image': row[4],
                    'status': row[5]
                }
                movies.append(movie)
    finally:
        connection.close()

    return movies
