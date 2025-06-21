import 'package:flutter/material.dart';

class CodeEmail extends StatefulWidget {
  const CodeEmail({super.key});

  @override
  State<CodeEmail> createState() => _CodeEmailState();
}

class _CodeEmailState extends State<CodeEmail> {

  final TextEditingController _codeEmail = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();


  // Función para validar el código de verificación
  String? _verificationCode(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor ingrese su código de verificación';
    }
    if (value.length != 5) {
      return 'El código debe tener exactamente 5 dígitos';
    }
    final RegExp codeRegex = RegExp(r'^\d{5}$'); // Expresión regular para 5 dígitos
    if (!codeRegex.hasMatch(value)) {
      return 'El código debe contener solo números';
    }
    // Simulación de que el código de verficación sea incorrecto
    if (value != '12345') {
      return 'El código de verificación es incorrecto';
    }
    return null;
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Confirmación de Correo'),
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
                  'Ingresa el código de confirmación que se envió a tu correo electrónico'
                ),
                const SizedBox(height: 16),
                TextFormField(
                  decoration: const InputDecoration(
                    hintText: 'Código de Verificación',
                    labelStyle: TextStyle(color: Colors.black),
                  ),
                  keyboardType: TextInputType.number,
                  controller: _codeEmail,
                  validator: _verificationCode, // Validación de código
                ),
                const SizedBox(height: 16),
                SizedBox(
                    width: double.infinity,
                    height: 48,
                    child: ElevatedButton(
                      onPressed: () {
                        if (_formKey.currentState!.validate()) {
                          // Si la validación es exitosa
                          print('Code: ${_codeEmail.text}');
                          print('Código válido');
                          Navigator.pushNamed(context, '/password');
                        }
                      },
                      style: OutlinedButton.styleFrom(
                          backgroundColor: Colors.purple,
                          foregroundColor: Colors.white,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(16),
                          )),
                      child: const Text('Enviar Código'),
                    )
                )
              ]
            ),
          ),
        ),
    );
  }
}