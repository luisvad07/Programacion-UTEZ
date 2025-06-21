import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  final Dio _dio = Dio(BaseOptions(
    baseUrl: 'http://192.168.100.96:8081/mac-morelos-api',
    connectTimeout: const Duration(seconds: 5), // Añade timeouts
    receiveTimeout: const Duration(seconds: 5),
  ));

  Future<Map<String, dynamic>?> login(String identifier, String password, BuildContext context) async {
    try {
      Response response = await _dio.post(
        '/auth/login',
        data: {
          'identifier': identifier,
          'password': password,
        },
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic>? data = response.data;
        if (data != null && data.containsKey('data') && data['data'] is Map) {
          final Map<String, dynamic> userData = data['data'];

          if (userData['role'] == 'CIUDADANO' && userData['allowedPlatforms'] == 'MOBILE') {
            SharedPreferences prefs = await SharedPreferences.getInstance();
            await prefs.setString('token', userData['token']);
            print('Token guardado: ${userData['token']}'); 
            return userData;
          } else {
            return {'error': true, 'message': 'Solo los ciudadanos pueden ingresar a la app móvil.'};
          }
        }
      }
    } on DioException catch (e) {
      if (e.response != null) {
        if (e.response!.statusCode == 401) {
          return {'error': true, 'message': 'Credenciales incorrectas.'};
        } else {
          return {'error': true, 'message': 'Error del servidor: ${e.response!.statusCode}'};
        }
      } else {
        return {'error': true, 'message': 'Error de conexión con el servidor.'};
      }
    } catch (e) {
      return {'error': true, 'message': 'Error inesperado: $e'};
    }

    return {'error': true, 'message': 'Error desconocido.'};
  }

  Future<void> logout() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.remove('token'); // Elimina el token
    // Aquí puedes añadir más lógica, como invalidar el token en el backend
  }

  Future<Map<String, dynamic>?> getProfile() async {
    try {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      String? token = prefs.getString('token'); // Obtener el token guardado

      if (token == null) {
        return null; // Importante: Devolver null si no hay token
      }

      // Establecer el token en los encabezados de la solicitud
      Response response = await _dio.get(
        '/users/getProfile',
        options: Options(
          headers: {
            'Authorization': 'Bearer $token', // Enviar el token en los encabezados
            'Content-Type': 'application/json', // Aclarar el tipo de contenido
            'Accept': 'application/json',    // Aceptar JSON
          },
        ),
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic>? data = response.data;
        if (data != null && data.containsKey('data') && data['data'] is Map) {
          final Map<String, dynamic> profileData = data['data'];
          return profileData; // Devuelve el objeto profileData
        } else {
          return null;
        }
      } else {
        return null; // Importante: Devolver null en caso de error
      }
    } on DioException { // Captura errores de Dio
      return null; // Importante: Devolver null en caso de error
    } catch (e) {
      return null; // Importante: Devolver null en caso de error
    }
  }

  Future<Map<String, dynamic>?> registerUser(Map<String, dynamic> userData) async {
    try {
      Response response = await _dio.post(
        '/users/create',
        data: userData,
      );

      if (response.statusCode == 201 || response.statusCode == 200) { // Cambiado a 201 para indicar creación exitosa
        return response.data; // Devuelve los datos del usuario creado
      } else {
        return {'error': true, 'message': 'Error al registrar el usuario. Código: ${response.statusCode}'};
      }
    } on DioException catch (e) {
      if (e.response != null) {
        return {'error': true, 'message': 'Error del servidor: ${e.response!.statusCode} - ${e.response!.data}'};
      } else {
        return {'error': true, 'message': 'Error de conexión con el servidor.'};
      }
    } catch (e) {
      return {'error': true, 'message': 'Error inesperado: $e'};
    }
  }
}