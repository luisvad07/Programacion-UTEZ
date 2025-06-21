import 'package:flutter/material.dart';

class CustomDialog extends StatelessWidget {
  final String title;
  final String message;
  final VoidCallback? onConfirm;

  const CustomDialog({
    super.key,
    required this.title,
    required this.message,
    this.onConfirm,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(title),
      content: Text(message),
      actions: <Widget>[
        TextButton(
          child: const Text('OK'),
          onPressed: () {
            Navigator.of(context).pop(); // Cierra el diálogo
            if (onConfirm != null) {
              onConfirm!(); // Ejecuta la función de confirmación si está definida
            }
          },
        ),
      ],
    );
  }
}