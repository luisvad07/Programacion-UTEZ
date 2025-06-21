
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:examen_u2_dmi/home/details/agregar_gasto.dart';
import 'package:examen_u2_dmi/home/details/object_details.dart';
import 'package:flutter/material.dart';

class Index extends StatefulWidget {
  const Index({super.key});

  @override
  State<Index> createState() => _IndexState();
}

class _IndexState extends State<Index> {
  final db = FirebaseFirestore.instance;
  List<Gastos> _gastos = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    db.collection("gastos").snapshots().listen((event) {
      _gastos = [];
      for (var doc in event.docs) {
        final data = doc.data();
        final gasto = Gastos(
          data['nombre'] ?? 'Sin nombre',
          data['cantidad'] ?? 0,
          data['description'] ?? 'Sin descripción',
          data['images'] ?? 'Sin Imagen'
        );
        _gastos.add(gasto);
      }

      if (mounted) {
        setState(() {
          isLoading = false;
        });
      } 
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Principal'),
        backgroundColor: const Color.fromARGB(255, 65, 73, 72),
        foregroundColor: Colors.white,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: _gastos.length,
              itemBuilder: (context, index) {
                final gasto = _gastos[index];
                return GestureDetector(
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => ObjectDetails(gastos: gasto),
                      ),
                    );
                  },
                  child: Card(
                    elevation: 4,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          ClipRRect(
                            borderRadius: BorderRadius.circular(12.0),
                            child: Image.network(
                              gasto.images,
                              width: 120,
                              height: 120,
                              fit: BoxFit.cover,
                            ),
                          ),
                          const SizedBox(width: 16),
                          Flexible(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  gasto.nombre,
                                  style: const TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.black,
                                  ),
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  gasto.description,
                                  style: const TextStyle(
                                    fontSize: 14,
                                    color: Colors.grey,
                                  ),
                                  maxLines: 2,
                                  overflow: TextOverflow.ellipsis,
                                ),
                                const SizedBox(height: 8),
                                Text(
                                  //gasto.cantidad.toString(),
                                  "\$${gasto.cantidad}",
                                  style: const TextStyle(
                                    fontSize: 14,
                                    color: Colors.green
                                  ),
                                  maxLines: 2,
                                  overflow: TextOverflow.ellipsis,
                                ),
                                const SizedBox(height: 8),
                                // Aquí se agrega el texto condicional para advertir sobre el gasto alto
                                Text(
                                  gasto.cantidad > 500
                                      ? "Gasto alto"
                                      : (gasto.cantidad > 250 ? "Cuidado" : "Normal"),
                                  style: TextStyle(
                                    color: gasto.cantidad > 500
                                        ? Colors.red
                                        : (gasto.cantidad > 250 ? Colors.orange : Colors.green),
                                    fontSize: 14,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                const SizedBox(height: 12),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            ),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => AgregarGasto(),
                  ),
                );
              },
              backgroundColor: const Color.fromARGB(255, 65, 73, 72),
              child: const Icon(Icons.add),
            ),
    );
  }
}