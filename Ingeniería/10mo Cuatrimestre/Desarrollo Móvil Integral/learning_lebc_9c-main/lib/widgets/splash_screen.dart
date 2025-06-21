import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();

    Future.delayed(const Duration(seconds: 3), () {
      FirebaseAuth.instance.authStateChanges().listen((User? user) {
        if (user != null) {
          //print(user.uid);
          //print('¡El usuario ha iniciado sesión!');
          Navigator.pushReplacementNamed(context, '/menu');
        } else {
          //print('¡El usuario no ha iniciado sesión!');
          Navigator.pushReplacementNamed(context, '/login');
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.orange,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center, // Centra el contenido
          children: [
            Image.asset(
              'assets/stop.png',
              width: 200,
              height: 200,
            ),
            const SizedBox(height: 32), // Espacio entre la imagen y el texto
            const Text(
              'Cargando...',
              style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
            ),
          ],
        ),
      ),
    );
  }
}
