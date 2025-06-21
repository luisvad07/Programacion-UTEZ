import 'package:flutter/material.dart';

class PasswordVerification extends StatefulWidget {
  const PasswordVerification({super.key});

  @override
  State<PasswordVerification> createState() => _PasswordVerificationState();
}

class _PasswordVerificationState extends State<PasswordVerification> {
  final TextEditingController _password = TextEditingController();
  final TextEditingController _confirmPassword = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  bool _isObscure = true;
  bool _isObscureConfirm = true;

  // Función para validar la contraseña
  String? _validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su contraseña';
    }
    if (value.length < 6) {
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
    }
    return null;
  }

  // Función para validar la repetición de la contraseña
  String? _validateConfirmPassword(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor repita su contraseña';
    }
    if (value != _password.text) {
      return 'Las contraseñas no coinciden';
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Verificación de Contraseña'),
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
                const Text(
                  'Ingresa y define las contraseñas para acceder al Sistema'
                ),
              const SizedBox(height: 16),
              // Campo para la contraseña
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
                    icon: Icon(
                      _isObscure ? Icons.visibility : Icons.visibility_off,
                    ),
                  ),
                ),
                controller: _password,
                obscureText: _isObscure,
                validator: _validatePassword, // Validación de contraseña
              ),
              const SizedBox(height: 16),
              
              // Campo para repetir la contraseña
              TextFormField(
                decoration: InputDecoration(
                  hintText: 'Repetir Contraseña',
                  labelStyle: const TextStyle(color: Colors.black),
                  icon: IconButton(
                    onPressed: () {
                      setState(() {
                        _isObscureConfirm = !_isObscureConfirm;
                      });
                    },
                    icon: Icon(
                      _isObscureConfirm
                          ? Icons.visibility
                          : Icons.visibility_off,
                    ),
                  ),
                ),
                controller: _confirmPassword,
                obscureText: _isObscureConfirm,
                validator: _validateConfirmPassword, // Validación de repetición de contraseña
              ),
              const SizedBox(height: 16),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      // Si la validación es exitosa
                      print('Contraseña válida y repetida correctamente');
                      Navigator.pushNamed(context, '/profile');
                    }
                  },
                  style: OutlinedButton.styleFrom(
                    backgroundColor: Colors.purple,
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                  ),
                  child: const Text('Verificar Contraseña'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
