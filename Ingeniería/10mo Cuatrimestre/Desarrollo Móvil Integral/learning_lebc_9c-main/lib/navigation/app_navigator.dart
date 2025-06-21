import 'package:flutter/material.dart';
import 'package:learning_lebc_9c/modules/home/screens/home.dart';
import 'package:learning_lebc_9c/modules/profile/screens/profile_screen.dart';
import 'package:learning_lebc_9c/modules/reservations/screens/reservation.dart';
import 'package:learning_lebc_9c/modules/top/screens/top_screen.dart';
import 'package:learning_lebc_9c/navigation/map_sample.dart';
import 'package:shared_preferences/shared_preferences.dart';

class AppNavigator extends StatefulWidget {
  const AppNavigator({required this.nextButton, super.key});
  final bool nextButton;

  @override
  State<AppNavigator> createState() => _AppNavigatorState();
}

class _AppNavigatorState extends State<AppNavigator> {  late final SharedPreferences prefs;
  int _selectedIndex = 0;

  static const List<Widget> _widgetOptions = <Widget>[
    Home(), // Página Home
    TopScreen(), // Página Top
    ReservationListScreen(), // Página Reservations
    ProfileScreen(), // Página Profile
    MapSample() //Página Mapas
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  void initState() {
    super.initState();
    _checkTutorial();
    /*(() async {
      prefs = await SharedPreferences.getInstance();
      final bool? tutorial = prefs.getBool('tutorial');
      if (tutorial != null) {
        if (!tutorial) {
          Navigator.pushReplacementNamed(context, '/tutorial');
        }
      } else {
        Navigator.pushReplacementNamed(context, '/tutorial');
      }
    })();*/
  }

  Future<void> _checkTutorial() async {
    if (!widget.nextButton) {
      final prefs = await SharedPreferences.getInstance();
      final bool? tutorial = prefs.getBool('tutorial');
      if (tutorial == null || !tutorial) {
        Navigator.pushReplacementNamed(context, '/tutorial');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions.elementAt(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Inicio',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.star_border),
            label: 'Top 5',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.list),
            label: 'Reservaciones',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person_outline),
            label: 'Perfil',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.map),
            label: 'Mapa',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        unselectedItemColor: Colors.grey,
        showSelectedLabels: true,
        onTap: _onItemTapped,
      ),
    );
  }
}
