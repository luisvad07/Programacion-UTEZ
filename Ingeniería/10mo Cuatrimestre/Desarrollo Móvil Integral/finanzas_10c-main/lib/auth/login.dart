import 'package:finanzas_10c/kernel/widgets/custom_dialog.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final TextEditingController _email = TextEditingController();
  final TextEditingController _password = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  bool _isObscure = true;

  // Función para validar el correo electrónico
  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su correo electrónico';
    }
    final RegExp emailRegex = RegExp(
        r'^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]+');
    if (!emailRegex.hasMatch(value)) {
      return 'Ingrese un correo electrónico válido';
    }
    return null;
  }

  // Función para validar la contraseña
  String? _validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su contraseña';
    }
    /*if (value.length < 6) {
      return 'La contraseña debe tener al menos 6 caracteres';
    }
    if (!RegExp(r'[A-Z]').hasMatch(value)) {
      return 'La contraseña debe contener al menos una letra mayúscula';
    }
    if (!RegExp(r'\d').hasMatch(value)) {
      return 'La contraseña debe contener al menos un número';
    }
    if (!RegExp(r'[!@#$%^&*(),.?":{}|<>]').hasMatch(value)) {
      return 'La contraseña debe contener al menos un carácter especial';
    }*/
    return null;
  }

  Future<void> _signIn() async {
    if (_formKey.currentState!.validate()) {
       //print('Email: ${_email.text}');
       //print('Password: ${_password.text}');
    try {
       final credential = await FirebaseAuth.instance.signInWithEmailAndPassword(
          email: _email.text,
          password: _password.text
        );

      // print(credential.user ?? 'No user');

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
                onConfirmed: () {
                  Navigator.pushNamed(context, '/email-verificator');
                },
              );
            },
          );
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
          //print('Wrong password provided for that user.');
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
        title: const Text('Inicio de Sesión'),
        backgroundColor: Colors.purple,
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset('assets/index.jfif', width: 300, height: 300),
              const SizedBox(height: 8),
              TextFormField(
                decoration: const InputDecoration(
                  hintText: 'Correo Electrónico',
                  labelStyle: TextStyle(color: Colors.black),
                ),
                keyboardType: TextInputType.emailAddress,
                controller: _email,
                validator: _validateEmail, // Validación de correo
              ),
              const SizedBox(height: 16),
              TextFormField(
                decoration: InputDecoration(
                  hintText: 'Contraseña',
                  labelStyle: const TextStyle(color: Colors.black),
                  icon: IconButton(
                    onPressed: () {
                      setState(() {
                        _isObscure = !_isObscure;
                      });
                    },
                    icon: Icon(_isObscure
                        ? Icons.visibility
                        : Icons.visibility_off),
                  ),
                ),
                controller: _password,
                obscureText: _isObscure,
                validator: _validatePassword, // Validación de contraseña
              ),
              const SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                onPressed: _signIn,
                  style: OutlinedButton.styleFrom(
                    backgroundColor: Colors.purple,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                  ),
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
              ),
              const SizedBox(height: 8),
              SizedBox(
                width: double.infinity,
                height: 40,
                child: GestureDetector(
                  onTap: () {
                    print('Se redirige a recuperar contraseña');
                  },
                  child: Container(
                    alignment: Alignment.center,
                    child: const Text(
                      'Recuperar Contraseña',
                      style: TextStyle(
                        color: Colors.purple,
                        fontSize: 14, // Ajusta el tamaño según sea necesario
                      ),
                    ),
                  ),
                ),
              ),
              
            ],
          ),
        ),
      ),
    );
  }
}
