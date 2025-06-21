import 'package:flutter/material.dart';

class Gastos {
  final String _nombre;
  final int _cantidad;
  final String _description;
  final String _images;

  Gastos(this._nombre, this._cantidad, this._description, this._images);

  String get nombre => _nombre;
  int get cantidad => _cantidad;
  String get description => _description;
  String get images => _images;

}

class ObjectDetails extends StatelessWidget {
  final Gastos gastos;

  const ObjectDetails({super.key, required this.gastos});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(gastos.nombre),
        backgroundColor: const Color.fromARGB(255, 65, 73, 72),
        foregroundColor: Colors.white,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Image.network(
                gastos.images,
                width: double.infinity,
                height: 200,
                fit: BoxFit.cover,
              ),
              const SizedBox(height: 16),
              Text(
                gastos.nombre,
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 8),
              Text(
                "\$${gastos.cantidad}",
                style: const TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.w500,
                  color: Colors.green,
                ),
              ),
              const SizedBox(height: 8),
              Text(
                gastos.description,
                style: const TextStyle(
                  fontSize: 16,
                ),
              ),
              const SizedBox(height: 8),
                Text(
                  gastos.cantidad > 500
                  ? "Gasto alto"
                  : (gastos.cantidad > 250 ? "Cuidado" : "Normal"),
                  style: TextStyle(
                    color: gastos.cantidad > 500
                    ? Colors.red
                    : (gastos.cantidad > 250 ? Colors.orange : Colors.green),
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    ),
                ),
              const SizedBox(height: 12),
            ],
          ),
        ),
      ),
    );
  }
}