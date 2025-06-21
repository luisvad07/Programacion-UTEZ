import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:learning_lebc_9c/modules/auth/register.dart';
import 'package:learning_lebc_9c/modules/tutorial/screens/tutorial.dart';
import 'firebase_options.dart';
import 'package:learning_lebc_9c/modules/auth/login.dart';
import 'package:learning_lebc_9c/modules/home/screens/home.dart';
import 'package:learning_lebc_9c/modules/profile/screens/profile_screen.dart';
import 'package:learning_lebc_9c/modules/reservations/screens/reservation.dart';
import 'package:learning_lebc_9c/modules/top/screens/top_screen.dart';
import 'package:learning_lebc_9c/navigation/app_navigator.dart';
import 'package:learning_lebc_9c/widgets/splash_screen.dart';

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
        '/menu': (context) => const AppNavigator(nextButton: false),
        '/home': (context) => const Home(),
        '/top': (context) => const TopScreen(),
        '/reservations': (context) => const ReservationListScreen(),
        '/profile': (context) => const ProfileScreen(),
        '/tutorial': (context) => const Tutorial()
      },
    );
  }
}
