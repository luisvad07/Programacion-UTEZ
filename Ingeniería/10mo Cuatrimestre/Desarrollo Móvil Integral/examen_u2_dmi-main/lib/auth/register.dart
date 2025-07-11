import 'package:examen_u2_dmi/kernel/widgets/custom_dialog.dart';
import 'package:examen_u2_dmi/kernel/widgets/field_password.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';

class Register extends StatefulWidget {
  const Register({super.key});

  @override
  State<Register> createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final TextEditingController _email = TextEditingController();
  final TextEditingController _password = TextEditingController();
  final TextEditingController _confirmPassword = TextEditingController();

  // Valida el correo electrónico
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

  Future<void> _register() async {

    if (_password.text != _confirmPassword.text) {
      await showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Error',
                message: '¡Las Contraseñas no coinciden!',
                icon: Icons.error_outline_outlined,
                iconColor: Colors.red,
                buttonText: 'Cerrar',
                onConfirmed: () {
                  //Navigator.of(context).pop();
                  Navigator.pushReplacementNamed(context, '/register');
                },
              );
            },
          );
      return;
    }
    
    if (_formKey.currentState!.validate()) {
      try {
        await FirebaseAuth.instance.createUserWithEmailAndPassword(
            email: _email.text,
            password: _password.text
        );

        await showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Éxito',
                message: '¡Se creó el Usuario con Éxito!',
                icon: Icons.check_circle_outline,
                iconColor: Colors.green,
                buttonText: 'Ir al Login',
                onConfirmed: () {
                  Navigator.pushReplacementNamed(context, '/login');
                },
              );
            },
        );

      } on FirebaseAuthException catch (e) {
        print('FirebaseAuthException code: ${e.code}');
        if (e.code == 'email-already-in-use') {
            await showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Error',
                message: '¡El Usuario ya existe en el Sistema!',
                icon: Icons.error_outline_outlined,
                iconColor: Colors.red,
                buttonText: 'Cerrar',
                onConfirmed: () {
                  Navigator.pushReplacementNamed(context, '/register');
                },
              );
            },
          );
        } else if (e.code == 'weak-password') {
            await showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Error',
                message: '¡La Contraseña es Débil!',
                icon: Icons.error_outline_outlined,
                iconColor: Colors.red,
                buttonText: 'Cerrar',
                onConfirmed: () {
                  Navigator.pushReplacementNamed(context, '/register');
                },
              );
            },
          );
        }
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Crear Cuenta'),
          backgroundColor: const Color.fromARGB(255, 65, 73, 72),
          foregroundColor: Colors.white,
        ),
        body: SingleChildScrollView(
            child: Padding(
          padding: const EdgeInsets.all(16.0),
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.center, children: [
            Image.asset('assets/market.jfif', width: 200, height: 200),
            const SizedBox(height: 16),
            Form(
                key: _formKey,
                child: Column(
                  children: [
                    TextFormField(
                      decoration: const InputDecoration(
                        icon: Icon(Icons.email),
                        hintText: 'Correo Electrónico',
                        labelStyle: TextStyle(color: Colors.black),
                      ),
                      keyboardType: TextInputType.emailAddress,
                      controller: _email,
                      validator: _validateEmail, // Validación de correo
                    ),
                    const SizedBox(height: 16),
                    TextFieldPassword(controller: _password),
                    const SizedBox(height: 16),
                    TextFieldPassword(
                      controller: _confirmPassword,
                      hintText: 'Confirmar Contraseña',
                    ),
                    const SizedBox(height: 16),
                    SizedBox(
                      width: double.infinity,
                      height: 48,
                      child: ElevatedButton(
                        onPressed: _register,
                        style: OutlinedButton.styleFrom(
                            backgroundColor: Colors.yellow,
                            foregroundColor: Colors.black,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(16),
                            )),
                        child: const Text('Crear Cuenta'),
                      ),
                    )
                  ],
                ))
          ]),
        )));
  }
}
