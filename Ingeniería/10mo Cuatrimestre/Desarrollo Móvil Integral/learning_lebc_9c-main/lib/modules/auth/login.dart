import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:learning_lebc_9c/kernel/widgets/custom_dialog.dart';
import 'package:learning_lebc_9c/kernel/widgets/field_password.dart';
import 'package:firebase_messaging/firebase_messaging.dart';

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final TextEditingController _email = TextEditingController();
  final TextEditingController _password = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  //bool _isObscure = true;
  //bool _loading = false;

  late FirebaseMessaging _messaging;

  @override
  void initState() {
    super.initState();
    _initializeFirebaseMessaging();
  }

  void _initializeFirebaseMessaging() async {
    _messaging = FirebaseMessaging.instance;

    // Solicita permisos
    NotificationSettings settings = await _messaging.requestPermission(
      alert: true,
      badge: true,
      sound: true,
    );

    print('Permisos de notificación: ${settings.authorizationStatus}');

    // Obtén el token
    String? token = await _messaging.getToken();
    print('FCM Token: $token');

    // Listener para notificaciones en foreground
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      print('Mensaje recibido: ${message.notification?.title}');
      // Manejar notificación
    });

    // Listener para notificaciones cuando se toca
    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
      print('Notificación abierta: ${message.notification?.title}');
      // Manejar redirección
    });
  }

  // Función para validar el correo electrónico
  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su correo electrónico';
    }
    final RegExp emailRegex = RegExp(r'^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]+');
    if (!emailRegex.hasMatch(value)) {
      return 'Ingrese un correo electrónico válido';
    }
    return null;
  }

  Future<void> _signIn() async {
    if (_formKey.currentState!.validate()) {
      try {
        final credential =
            await FirebaseAuth.instance.signInWithEmailAndPassword(
          email: _email.text,
          password: _password.text,
        );

        //print(credential.user ?? 'No user');

        if (credential.user != null) {
          await showDialog(
            context: context,
            barrierDismissible: false, // Evitar cerrar tocando fuera
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Éxito',
                message: '¡Bienvenido al Sistema!',
                icon: Icons.check_circle_outline,
                iconColor: Colors.green,
                buttonText: 'Entrar al Sistema',
                //buttonColor: Colors.green,
                onConfirmed: () {
                  Navigator.pushReplacementNamed(context, '/menu');
                },
              );
            },
          );

          await Future.delayed(const Duration(seconds: 6));
          //print('Navegando a /menu');
          Navigator.pushReplacementNamed(context, '/menu');
        } else {
          print('El usuario no está autenticado.');
        }
      } on FirebaseAuthException catch (e) {
        print('FirebaseAuthException code: ${e.code}');
        if (e.code == 'invalid-credential') {
          await showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Error',
                message: '¡El Usuario y Contraseña son Incorrectos!',
                icon: Icons.error_outline_outlined,
                iconColor: Colors.red,
                buttonText: 'Cerrar',
                onConfirmed: () {
                  //Navigator.of(context).pop();
                  Navigator.pushReplacementNamed(context, '/login');
                },
              );
            },
          );
        } else if (e.code == 'wrong-password') {
          print('Wrong password provided for that user.');
        }
      } catch (e) {
        print('Error inesperado: $e');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Iniciar Sesión'),
          backgroundColor: const Color.fromARGB(255, 26, 207, 180),
        ),
        body: SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Form(
              key: _formKey,
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset('assets/init.jfif', width: 200, height: 200),
                    const SizedBox(height: 16),
                    TextFormField(
                      decoration: const InputDecoration(
                        icon: Icon(Icons.email),
                        hintText: 'Correo Electrónico',
                        //label: Text('Correo Electrónico'),
                        labelStyle: TextStyle(color: Colors.black),
                      ),
                      keyboardType: TextInputType.emailAddress,
                      controller: _email,
                      validator: _validateEmail, // Validación de correo
                    ),
                    const SizedBox(height: 16),
                    TextFieldPassword(
                      controller: _password,
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      width: double.infinity,
                      height: 48,
                      child: ElevatedButton(
                        onPressed: _signIn,
                        style: OutlinedButton.styleFrom(
                            backgroundColor: Colors.pink,
                            foregroundColor: Colors.white,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(16),
                            )),
                        child: const Text('Iniciar Sesión'),
                      ),
                    ),
                    const SizedBox(height: 8),
                    InkWell(
                      onTap: () => Navigator.pushNamed(context, '/register'),
                      child: const Text(
                        '¿Eres nuevo? Registrate',
                        style: TextStyle(
                            color: Colors.blue,
                            decoration: TextDecoration.underline,
                            decorationColor: Colors.blue),
                      ),
                    )
                  ]),
            )));
  }
}
