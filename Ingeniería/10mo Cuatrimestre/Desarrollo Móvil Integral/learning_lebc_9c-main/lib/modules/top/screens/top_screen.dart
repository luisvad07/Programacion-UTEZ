import 'package:flutter/material.dart';

class TopScreen extends StatefulWidget {
  const TopScreen({super.key});

  @override
  TopScreenState createState() => TopScreenState();
}

class TopScreenState extends State<TopScreen> {
  final List<String> topItems = [
    'Manzana',
    'Plátano',
    'Uva',
    'Naranja',
    'Limón',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Artículos principales'),
        backgroundColor: const Color.fromARGB(255, 26, 207, 180),
      ),
      // Botón flotante
      /*floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.orange,
        foregroundColor: Colors.white,
        onPressed: () {
          Navigator.pushNamed(context, '/profile'); // Navega a la pantalla de perfil
        },
        child: const Icon(Icons.person),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,*/
      body: ListView.builder(
        itemCount: topItems.length,
        itemBuilder: (context, index) {
          return ListTile(
            leading: CircleAvatar(
              backgroundColor: Colors.green,
              child: Text('${index + 1}'), // Muestra el número del ítem
            ),
            title: Text(topItems[index]),
          );
        },
      ),
    );
  }
}
