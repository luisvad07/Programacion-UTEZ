import 'package:finanzas_10c/kernel/widgets/custom_dialog.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';

class Register extends StatefulWidget {
  const Register({super.key});

  @override
  State<Register> createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  //Global para el formulario
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final TextEditingController _email = TextEditingController();
  final TextEditingController _password = TextEditingController();
  final TextEditingController _confirmPassword = TextEditingController();
  bool _isObscure = true;
  bool _isObscureConfirm = true;

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

  String? _validateConfirmPassword(String? value) {
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
      //print('Email: ${_email.text}');
      //print('Password: ${_password.text}');
      try {
        //final credential = 
        await FirebaseAuth.instance.createUserWithEmailAndPassword(
            email: _email.text,
            password: _password.text
        );
                              
        //print(credential.user ?? 'No se creó el usuario.');

        await showDialog(
            context: context,
            barrierDismissible: false, // Evitar cerrar tocando fuera
            builder: (BuildContext context) {
              return CustomDialog(
                title: 'Éxito',
                message: '¡Se creó el Usuario con Éxito!',
                icon: Icons.check_circle_outline,
                iconColor: Colors.green,
                buttonText: 'Ir al Login',
                //buttonColor: Colors.green,
                onConfirmed: () {
                  Navigator.pushNamedAndRemoveUntil(context, '/login', (Route<dynamic> route) => false,);
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
                  //Navigator.of(context).pop();
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
                  //Navigator.of(context).pop();
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
              TextFormField(
                decoration: InputDecoration(
                  hintText: 'Confirmar Contraseña',
                  labelStyle: const TextStyle(color: Colors.black),
                  icon: IconButton(
                    onPressed: () {
                      setState(() {
                        _isObscureConfirm = !_isObscureConfirm;
                      });
                    },
                    icon: Icon(_isObscureConfirm
                        ? Icons.visibility
                        : Icons.visibility_off),
                  ),
                ),
                controller: _confirmPassword,
                obscureText: _isObscureConfirm,
                validator: _validateConfirmPassword, // Validación de contraseña
              ),
              const SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                onPressed: _register,
                  style: OutlinedButton.styleFrom(
                    backgroundColor: Colors.purple,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                  ),
                  child: const Text('Crear Cuenta'),
                ),
              ),
            ],
          ),
        ),
      ),
      );
  }
}
