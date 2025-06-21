import 'package:flutter/material.dart';
import 'package:mac_morelos_movil_flutter/modules/dashboard/screens/dashboard.dart';
import 'package:mac_morelos_movil_flutter/modules/events/screens/event_registration.dart';
import 'package:mac_morelos_movil_flutter/modules/maps/screens/mapa_screen.dart';
import 'package:mac_morelos_movil_flutter/modules/profile/screens/profile_screen.dart';
// Asegúrate de tener las rutas correctas a tus pantallas


class AppNavigator extends StatefulWidget {
  const AppNavigator({super.key});

  @override
  State<AppNavigator> createState() => _AppNavigatorState();
}

class _AppNavigatorState extends State<AppNavigator> {
  int _selectedIndex = 0;

  // Lista de widgets para las opciones de navegación
  static final List<Widget> _widgetOptions = <Widget>[
    const Dashboard(), // Página Home
    const EventRegistration(),
    const ProfileScreen(), // Página Profile
    const MapaScreen()
  ];

  // Método para cambiar la página seleccionada
  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions.elementAt(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Dashboard',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.event_available),
            label: 'Eventos',
          ),
          /*BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Noticias',
          ),*/
          BottomNavigationBarItem(
            icon: Icon(Icons.person_outline),
            label: 'Perfil',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.map),
            label: 'Mapa',
          )
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: const Color(0xFF70795B), // Color seleccionado
        unselectedItemColor: Colors.grey, // Color no seleccionado
        showSelectedLabels: true, // Mostrar etiquetas cuando está seleccionado
        showUnselectedLabels: false, // No mostrar etiquetas cuando no está seleccionado
        type: BottomNavigationBarType.fixed, // Evita el desplazamiento
        backgroundColor: const Color(0xFFCDD2C0), // Color de fondo
        onTap: _onItemTapped, // Llama al método al tocar un ítem
      ),
    );
  }
}