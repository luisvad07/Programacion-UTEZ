import 'package:flutter/material.dart';
import 'package:learning_lebc_9c/navigation/app_navigator.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Tutorial extends StatefulWidget {
  const Tutorial({super.key});

  @override
  State<Tutorial> createState() => _TutorialState();
}

class _TutorialState extends State<Tutorial> {
  void _onItemTapped() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool('tutorial', true);
    Navigator.pushReplacementNamed(context, '/menu');
  }

  /*void _nextTapped() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setBool('tutorial', false);
    Navigator.pushReplacementNamed(context, '/menu');
  }*/

  @override
Widget build(BuildContext context) {
  return Scaffold(
    body: Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 32),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Image.asset(
            'assets/init.jfif',
            width: double.infinity,
            height: 240,
            fit: BoxFit.cover,
          ),
          const SizedBox(height: 24),
          const Center(
            child: Text(
              'Bienvenido a la aplicación',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 26,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
            ),
          ),
          const SizedBox(height: 12),
          const Text(
            'Tu compañero ideal para organizar tus reservaciones en los restaurantes. ¡Vamos a comenzar!',
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 16,
              color: Colors.black54,
            ),
          ),
          const Spacer(),
          ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.amber[800],
              foregroundColor: Colors.black,
              padding: const EdgeInsets.symmetric(horizontal: 32, vertical: 12),
              textStyle: const TextStyle(fontSize: 18),
            ),
            onPressed: () => Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                builder: (context) => const AppNavigator(nextButton: true),
              ),
            ),
            child: const Text('Comenzar'),
          ),
          const SizedBox(height: 8),
          TextButton(
            onPressed: _onItemTapped,
            child: const Text(
              'No mostrar de nuevo',
              style: TextStyle(color: Colors.blueAccent, fontSize: 16),
            ),
          ),
          const SizedBox(height: 20),
          LinearProgressIndicator(
            value: 0.3,
            backgroundColor: Colors.grey[200],
            valueColor: AlwaysStoppedAnimation<Color>(Colors.amber[800]!),
          ),
        ],
      ),
    ),
  );
}

}
