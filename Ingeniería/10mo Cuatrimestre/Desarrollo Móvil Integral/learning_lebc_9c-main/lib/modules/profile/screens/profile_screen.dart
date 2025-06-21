import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:learning_lebc_9c/kernel/widgets/custom_dialog.dart';

class ProfileScreen extends StatelessWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // Obtener el usuario actual
    User? user = FirebaseAuth.instance.currentUser;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Perfil'),
        backgroundColor: const Color.fromARGB(255, 26, 207, 180),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const CircleAvatar(
              radius: 50,
              backgroundImage: AssetImage('assets/jak-daxter.jpg'),
            ),
            const SizedBox(height: 16),
            const Text(
              'Luis Valladolid',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              user != null ? user.email! : 'No has iniciado sesión',
              style: const TextStyle(fontSize: 16, color: Colors.grey),
            ),
            const SizedBox(height: 16),
            Expanded(
              child: ListView(
                children: [
                  ListTile(
                    leading: const Icon(Icons.settings),
                    title: const Text('Configuración'),
                    onTap: () {
                      // Maneja el tap en Settings
                    },
                  ),
                  ListTile(
                    leading: const Icon(Icons.history),
                    title: const Text('Historial de Pedidos'),
                    onTap: () {
                      // Maneja el tap en Order History
                    },
                  ),
                  ListTile(
                    leading: const Icon(Icons.logout),
                    title: const Text('Cerrar Sesión'),
                    onTap: () async {
                      showDialog(
                        context: context,
                        barrierDismissible: false, // Evitar cerrar tocando fuera
                        builder: (BuildContext context) {
                          return CustomDialog(
                            title: 'Confirmación',
                            message: '¿Estas seguro de Cerrar Sesión?',
                            icon: Icons.warning_amber_outlined,
                            iconColor: Colors.yellow,
                            buttonText: 'Cerrar Sesión',
                            //buttonColor: Colors.green,
                            onConfirmed: () async {
                              //print("Cerrando Sesión");
                              //Navigator.of(context).pop();
                              await FirebaseAuth.instance.signOut();
                              Navigator.pushReplacementNamed(context, '/login');
                            },
                          );
                        },
                      );
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
