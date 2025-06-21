import 'package:finanzas_10c/auth/login.dart';
import 'package:finanzas_10c/auth/register.dart';
import 'package:finanzas_10c/screens/code/code_email.dart';
import 'package:finanzas_10c/screens/email/email_verification.dart';
import 'package:finanzas_10c/screens/password/password_verfication.dart';
import 'package:finanzas_10c/screens/profile.dart';
import 'package:finanzas_10c/widgets/splash_screen.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(); // Inicializa Firebase
  runApp(MyApp());
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
        '/email-verificator': (context) => const EmailVerification(),
        '/code-email': (context) => const CodeEmail(),
        '/password': (context) => const PasswordVerification(),
        '/register': (context) => const Register(),
        '/profile': (context) => const Profile(),
      },
    );
  }
}
