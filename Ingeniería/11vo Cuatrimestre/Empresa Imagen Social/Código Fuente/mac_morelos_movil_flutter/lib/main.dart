import 'package:flutter/material.dart';
import 'package:mac_morelos_movil_flutter/modules/auth/login.dart';
import 'package:mac_morelos_movil_flutter/modules/auth/register.dart';
import 'package:mac_morelos_movil_flutter/modules/dashboard/screens/dashboard.dart';
import 'package:mac_morelos_movil_flutter/modules/events/screens/event_registration.dart';
import 'package:mac_morelos_movil_flutter/modules/maps/screens/mapa_screen.dart';
import 'package:mac_morelos_movil_flutter/modules/profile/screens/profile_screen.dart';
import 'package:mac_morelos_movil_flutter/modules/widgets/app_navigator.dart';
import 'package:mac_morelos_movil_flutter/modules/widgets/splash_screen.dart';

void main() {
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
          '/dashboard': (context) => const Dashboard(),
          '/eventos': (context) => const EventRegistration(),
          '/profile': (context) => const ProfileScreen(),
          '/mapa': (context) => const MapaScreen()
          /*'/tutorial': (context) => const Tutorial()*/
        },
    );
  }
}
