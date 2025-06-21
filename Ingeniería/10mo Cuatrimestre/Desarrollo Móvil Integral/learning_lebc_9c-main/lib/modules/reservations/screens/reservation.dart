import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class ReservationListScreen extends StatefulWidget {
  const ReservationListScreen({super.key});

  @override
  State<ReservationListScreen> createState() => _ReservationListScreenState();
}

class _ReservationListScreenState extends State<ReservationListScreen> {
 List<Map<String, dynamic>> reservations = [];
  bool isLoading = true;
  final Dio dio = Dio();

  @override
  void initState() {
    super.initState();
    fetchReservations();
  }

  Future<void> fetchReservations() async {
    try {
      // Hacemos la solicitud GET a la API
      Response response = await dio.get('https://jsonplaceholder.typicode.com/users');
      
      // Si la respuesta es exitosa (200), guardamos los datos
      if (response.statusCode == 200) {
        setState(() {
          // Convertimos la respuesta en una lista de mapas
          reservations = List<Map<String, dynamic>>.from(response.data);
          isLoading = false;
        });
      }
    } catch (e) {
      print('Error fetching reservations: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Lista de Usuarios'),
        backgroundColor: const Color.fromARGB(255, 26, 207, 180),
      ),
      /*floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.orange,
        foregroundColor: Colors.white,
        onPressed: () {
          Navigator.pushNamed(context, '/top');
        },
        child: const Icon(Icons.list),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,*/
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: reservations.length,
              itemBuilder: (context, index) {
                final reservation = reservations[index];
                return ListTile(
                  leading: const Icon(Icons.person),
                  title: Text(reservation['name'] ?? 'No Name'),
                  subtitle: Text('Email: ${reservation['email']}'),
                  trailing: Text('Phone: ${reservation['phone']}'),
                );
              },
            ),
    );
  }
}
