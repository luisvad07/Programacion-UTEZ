import 'dart:async';
import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';

// Modelos
class Event {
  final int eventId;
  final String name;
  final String description;
  final DateTime startDate;
  final DateTime endDate;
  final String status; // ACTIVO, INACTIVO, etc.
  final Address address; // Añade la propiedad address
  final String location; // Añade la propiedad location

  Event({
    required this.eventId,
    required this.name,
    required this.description,
    required this.startDate,
    required this.endDate,
    required this.status,
    required this.address,
    required this.location,
  });

  factory Event.fromJson(Map<String, dynamic> json) {
    return Event(
      eventId: json['eventId'],
      name: json['name'],
      description: json['description'],
      startDate: DateTime.parse(json['startDate']),
      endDate: DateTime.parse(json['endDate']),
      status: json['statusEvent'] ?? 'UNKNOWN',
      address: Address.fromJson(json['address']), // Convierte el JSON de address
      location: json['location'] ?? '',
    );
  }
}

class Address {
  final int addressId;
  final String street;
  final String number;
  final String interiorNumber;
  final String neighborhood;
  final String zipCode;
  final String city;
  final String state;
  final String country;
  final double latitude;
  final double longitude;

  Address({
    required this.addressId,
    required this.street,
    required this.number,
    required this.interiorNumber,
    required this.neighborhood,
    required this.zipCode,
    required this.city,
    required this.state,
    required this.country,
    required this.latitude,
    required this.longitude,
  });

  factory Address.fromJson(Map<String, dynamic> json) {
    return Address(
      addressId: json['addressId'],
      street: json['street'] ?? '',
      number: json['number'] ?? '',
      interiorNumber: json['interiorNumber'] ?? '',
      neighborhood: json['neighborhood'] ?? '',
      zipCode: json['zipCode'] ?? '',
      city: json['city'] ?? '',
      state: json['state'] ?? '',
      country: json['country'] ?? '',
      latitude: (json['latitude'] as num).toDouble(),
      longitude: (json['longitude'] as num).toDouble(),
    );
  }
}


// Servicios
class EventsService {
  final Dio _dio = Dio();
  final String baseUrl;

  EventsService({required this.baseUrl});

  Future<String?> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? token = prefs.getString('token');
    return token;
  }

  Future<List<Event>> getActiveEvents() async {
    final token = await getToken();
    if (token == null) {
      throw Exception('No se encontró el token de autenticación.');
    }

    _dio.options.headers['Authorization'] = 'Bearer $token';
    try {
      final response = await _dio.get('$baseUrl/events/status/ACTIVO');

      if (response.statusCode == 200) {
        // Ajusta esto según la estructura de tu respuesta JSON
        // Si tu API devuelve un objeto con una propiedad 'data' que contiene la lista:
        // final List<dynamic> eventListJson = response.data['data'];
        // Si la lista está directamente en la respuesta:
        final List<dynamic> eventListJson = response.data['data']; // O response.data si la lista está directamente ahí.

        List<Event> events = eventListJson.map((json) => Event.fromJson(json)).toList();
        return events;
      } else {
        throw Exception('Failed to load events: ${response.statusCode}');
      }
    } on DioException catch (e) {
      if (e.response?.statusCode == 403) {
        throw Exception('No tienes permisos para acceder a este recurso.');
      } else if (e.response?.statusCode == 401) {
        throw Exception('Token inválido o expirado.  Por favor, inicia sesión nuevamente.');
      }


      throw Exception('Error al obtener eventos: ${e.message}');
    } catch (e) {
      throw Exception('Error inesperado: $e');
    }
  }

  Future<List<Event>> getUpcomingEvents() async {
    final token = await getToken();
    if (token == null) {
      throw Exception('No se encontró el token de autenticación.');
    }

    _dio.options.headers['Authorization'] = 'Bearer $token';
    try {
      // Formatear la fecha actual en formato ISO 8601
      String nowStr = DateTime.now().toUtc().toIso8601String();

      // Construir la URL con el parámetro 'now'
      String url = '$baseUrl/events/ongoing?now=$nowStr';

      final response = await _dio.get(url);

      if (response.statusCode == 200) {
        final List<dynamic> eventListJson = response.data['data']; // Ajusta según tu API

        List<Event> events = eventListJson.map((json) => Event.fromJson(json)).toList();
        return events;
      } else {
        throw Exception('Failed to load upcoming events: ${response.statusCode}');
      }
    } on DioException catch (e) {
      if (e.response?.statusCode == 403) {
        throw Exception('No tienes permisos para acceder a este recurso.');
      } else if (e.response?.statusCode == 401) {
        throw Exception('Token inválido o expirado.  Por favor, inicia sesión nuevamente.');
      }
      throw Exception('Error al obtener eventos próximos: ${e.message}');
    } catch (e) {
      throw Exception('Error inesperado: $e');
    }
  }
}