import 'package:flutter/material.dart';

class TextFieldPassword extends StatefulWidget {
  final TextEditingController controller;
  final String hintText;
  final String labelText;

  const TextFieldPassword({
    super.key,
    required this.controller,
    this.hintText = 'Contraseña',
    this.labelText = 'Contraseña',
  });

  @override
  _TextFieldPasswordState createState() => _TextFieldPasswordState();
}

class _TextFieldPasswordState extends State<TextFieldPassword> {
  bool _isObscure = true;

  // Función para validar la contraseña
  String? _validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su contraseña';
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      controller: widget.controller,
      validator: _validatePassword,
      obscureText: _isObscure,
      decoration: InputDecoration(
        hintText: widget.hintText,
        labelStyle: const TextStyle(color: Colors.black),
        suffixIcon: IconButton(
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
    );
  }
}
