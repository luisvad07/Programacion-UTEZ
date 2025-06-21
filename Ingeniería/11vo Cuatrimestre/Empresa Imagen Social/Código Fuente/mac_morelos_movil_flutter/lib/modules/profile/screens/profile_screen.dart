import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mac_morelos_movil_flutter/kernel/services/auth_service.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  Map<String, dynamic>? userProfile;
  final AuthService _authService = AuthService(); // Instancia del servicio

  @override
  void initState() {
    super.initState();
    _loadProfile();
  }

  void _loadProfile() async {
    AuthService authService = AuthService();
    var profile = await authService.getProfile();

    if (profile != null) {
      setState(() {
        userProfile = profile;
      });
    }
  }

  void _showLogoutDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Cerrar Sesión"),
          content: const Text("¿Estás seguro de que deseas cerrar sesión?"),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(), // Cierra el diálogo
              child: const Text("Cancelar", style: TextStyle(color: Colors.grey)),
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              onPressed: () {
                Navigator.of(context).pop(); // Cierra el diálogo
                _logout(); // Llama a la función de cerrar sesión
              },
              child: const Text("Sí, salir", style: TextStyle(color: Colors.white)),
            ),
          ],
        );
      },
    );
  }


  // Función para cerrar sesión
  void _logout() async {
    await _authService.logout(); // Llama al método de logout
    // Redirige al usuario a la pantalla de inicio de sesión
    Navigator.of(context).pushNamedAndRemoveUntil('/login', (Route<dynamic> route) => false);
  }

  String formatDate(String dateString) {
    try {
      DateTime parsedDate = DateTime.parse(dateString);
      return DateFormat('dd/MM/yyyy HH:mm:ss').format(parsedDate);
    } catch (e) {
      return "Fecha no válida";
    }
  }

  String formatDateBirth(String dateString) {
    try {
      DateTime parsedDate = DateTime.parse(dateString);
      return DateFormat('dd/MM/yyyy').format(parsedDate);
    } catch (e) {
      return "Fecha no válida";
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Perfil"),
        backgroundColor: const Color(0xFF5B6479),
        titleTextStyle: const TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.bold,
          color: Colors.white,
        ),
        centerTitle: true
      ),
      backgroundColor: const Color.fromARGB(255, 30, 36, 16),
      body: userProfile == null
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(20.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  // Imagen de perfil circular
                  Center(
                    child: CircleAvatar(
                      radius: 70,
                      backgroundImage: userProfile!["imgProfile"] != null
                          ? NetworkImage(userProfile!["imgProfile"])
                          : const AssetImage('assets/simac.png') as ImageProvider,
                    ),
                  ),
                  const SizedBox(height: 30),

                  // Información del perfil
                  _buildProfileInfo("Nombre", "${userProfile!["firstName"]} ${userProfile!["lastName"]}"),
                  _buildProfileInfo("Correo", userProfile!["email"]),
                  _buildProfileInfo("Teléfono", userProfile!["phone"]),
                  _buildProfileInfo("Usuario", userProfile!["username"]),
                  _buildProfileInfo("CURP", userProfile!["curp"]),
                  _buildProfileInfo("RFC", userProfile!["rfc"]),
                  _buildProfileInfo("Fecha de nacimiento", formatDateBirth(userProfile!["birthdate"])),
                  _buildProfileInfo("Rol", userProfile!["role"]?["name"] ?? "N/A"), //Manejo nulo
                  _buildProfileInfo("Descripción del Rol", userProfile!["role"]?["description"] ?? "N/A"), //Manejo nulo
                  _buildProfileInfo("Fecha de Creación", userProfile!["createdAt"] != null ? formatDate(userProfile!["createdAt"]) : "Aún no se ha creado el perfil"),
                  _buildProfileInfo("Fecha de Modificación", userProfile!["updatedAt"] != null ? formatDate(userProfile!["updatedAt"]) : "Aún no se ha modificado el perfil"),
                  const SizedBox(height: 30),
                  ElevatedButton.icon(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.red, // Color del botón
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                      padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 20),
                    ),
                    onPressed: _showLogoutDialog,
                    icon: const Icon(Icons.logout, color: Colors.white),
                    label: const Text("Cerrar Sesión", style: TextStyle(color: Colors.white, fontSize: 18)),
                  ),
                ],
              ),
            ),
    );
  }

  Widget _buildProfileInfo(String label, String? value) {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
      color: Colors.white,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(label, style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: Color(0xFF5B6479))),
            const SizedBox(height: 5),
            Text(value ?? "No disponible", style: const TextStyle(fontSize: 18, color: Color(0xFF70795B))),
          ],
        ),
      ),
    );
  }
}