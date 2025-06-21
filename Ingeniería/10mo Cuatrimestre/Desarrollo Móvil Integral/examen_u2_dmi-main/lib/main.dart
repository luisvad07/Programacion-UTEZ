import 'package:examen_u2_dmi/auth/login.dart';
import 'package:examen_u2_dmi/auth/register.dart';
import 'package:examen_u2_dmi/firebase_options.dart';
import 'package:examen_u2_dmi/kernel/widgets/app_navigator.dart';
import 'package:examen_u2_dmi/kernel/widgets/splash_screen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      initialRoute: '/',
      routes: {
        '/': (context) => const SplashScreen(),
        '/login': (context) => const Login(),
        '/register': (context) => const Register(),
        '/menu': (context) => const AppNavigator(),
      },
    );
  }
}
