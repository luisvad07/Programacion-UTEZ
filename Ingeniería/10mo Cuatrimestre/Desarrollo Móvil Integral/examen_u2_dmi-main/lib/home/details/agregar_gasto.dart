import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';

class AgregarGasto extends StatefulWidget {
  @override
  _AgregarGastoState createState() => _AgregarGastoState();
}

class _AgregarGastoState extends State<AgregarGasto> {
  final _nombreController = TextEditingController();
  final _descriptionController = TextEditingController();
  final _imagesController = TextEditingController();
  final _cantidadController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  void _guardarGasto() {
    if (_formKey.currentState!.validate()) {
      final nombre = _nombreController.text;
      final description = _descriptionController.text;
      final images = _imagesController.text;
      final cantidad = int.tryParse(_cantidadController.text) ?? 0;

      FirebaseFirestore.instance.collection('gastos').add({
        'nombre': nombre,
        'description': description,
        'images': images,
        'cantidad': cantidad,
      }).then((_) {
        Navigator.pop(context);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Agregar Gasto"),
        backgroundColor: const Color.fromARGB(255, 65, 73, 72),
        foregroundColor: Colors.white,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Image.asset('assets/market.jfif', width: 200, height: 200),
              TextFormField(
                controller: _nombreController,
                decoration: const InputDecoration(
                  labelText: 'Nombre del gasto',
                  icon: Icon(Icons.label),
                ),
                validator: (value) => value!.isEmpty ? 'Ingrese el nombre del gasto' : null,
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _descriptionController,
                decoration: const InputDecoration(
                  labelText: 'Descripción',
                  icon: Icon(Icons.description),
                ),
                validator: (value) => value!.isEmpty ? 'Ingrese una descripción' : null,
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _imagesController,
                decoration: const InputDecoration(
                  labelText: 'URL de la imagen',
                  icon: Icon(Icons.image),
                ),
                keyboardType: TextInputType.url,
                validator: (value) => value!.isEmpty ? 'Ingrese una URL de imagen' : null,
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _cantidadController,
                decoration: const InputDecoration(
                  labelText: 'Cantidad',
                  icon: Icon(Icons.attach_money),
                ),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Ingrese la cantidad';
                  } else if (int.tryParse(value) == null || int.parse(value) < 0) {
                    return 'Ingrese una cantidad válida';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 20),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: Center(
                  child: ElevatedButton.icon(
                    onPressed: _guardarGasto,
                    icon: const Icon(Icons.save),
                    label: const Text("Guardar"),
                    style: ElevatedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30),
                      ),
                      backgroundColor: Colors.yellow,
                      foregroundColor: Colors.black,
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

  @override
  void dispose() {
    _nombreController.dispose();
    _descriptionController.dispose();
    _imagesController.dispose();
    _cantidadController.dispose();
    super.dispose();
  }
}
