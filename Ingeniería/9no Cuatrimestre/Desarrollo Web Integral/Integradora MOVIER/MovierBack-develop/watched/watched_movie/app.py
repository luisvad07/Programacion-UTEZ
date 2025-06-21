import json
from utils import get_connection



headers_open = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,OPTIONS',
    }

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])
        user_id = body.get('user_id')
        movie_id = body.get('movie_id')

        if not isinstance(user_id, int) or not isinstance(movie_id, int):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'Los IDs de usuario y película deben ser enteros'})
            }

        if not user_id or not movie_id:
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'Faltan user_id o movie_id en el cuerpo de la solicitud'})
            }

        if not is_valid_user(user_id):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'El usuario no existe o no es válido'})
            }

        if not is_active_movie(movie_id):
            return {
                'statusCode': 400,
                'headers': headers_open,
                'body': json.dumps({'message': 'La película no existe o no está activa'})
            }

        was_watched = mark_movie_as_watched(user_id, movie_id)

        if was_watched:
            return {
                'statusCode': 200,
                'headers': headers_open,
                'body': json.dumps({'message': 'Película desmarcada como vista'})
            }
        else:
            return {
                'statusCode': 200,
                'headers': headers_open,
                'body': json.dumps({'message': 'Película marcada como vista con éxito'})
            }

    except Exception as e:
        return {
            'statusCode': 500,
            'headers': headers_open,
            'body': json.dumps(
                {'message': 'Error al marcar la película como vista en la base de datos', 'error': str(e)})
        }


def is_valid_user(user_id):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            query = "SELECT id FROM Users WHERE id = %s"
            cursor.execute(query, (user_id,))
            result = cursor.fetchone()
            if result:
                return True
            return False
    finally:
        connection.close()


def is_active_movie(movie_id):
    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            query = "SELECT id FROM Movies WHERE id = %s AND status = 1"
            cursor.execute(query, (movie_id,))
            result = cursor.fetchone()
            if result:
                return True
            return False
    finally:
        connection.close()


def mark_movie_as_watched(user_id, movie_id):
    connection = get_connection()

    try:
        with connection.cursor() as cursor:
            check_query = """
                SELECT status
                FROM WatchedMovies
                WHERE user_id = %s AND movie_id = %s
            """
            cursor.execute(check_query, (user_id, movie_id))
            result = cursor.fetchone()

            if result:
                current_status = result[0]
                if current_status == 1:
                    update_query = """
                        UPDATE WatchedMovies
                        SET status = 0
                        WHERE user_id = %s AND movie_id = %s
                    """
                    cursor.execute(update_query, (user_id, movie_id))
                    connection.commit()
                    return True  # Se desmarcó la película
                else:
                    update_query = """
                        UPDATE WatchedMovies
                        SET status = 1
                        WHERE user_id = %s AND movie_id = %s
                    """
                    cursor.execute(update_query, (user_id, movie_id))
                    connection.commit()
                    return False
            else:
                insert_query = """
                    INSERT INTO WatchedMovies (user_id, movie_id, status)
                    VALUES (%s, %s, 1)
                """
                cursor.execute(insert_query, (user_id, movie_id))
                connection.commit()
                return False

    finally:
        connection.close()
