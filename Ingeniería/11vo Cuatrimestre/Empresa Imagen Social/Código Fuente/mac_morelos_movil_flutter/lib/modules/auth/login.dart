import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_svg/svg.dart';
import 'package:mac_morelos_movil_flutter/kernel/services/auth_service.dart';
import 'package:mac_morelos_movil_flutter/modules/widgets/particle_painter.dart';
import 'package:shared_preferences/shared_preferences.dart'; // Importa shared_preferences para guardar el token

class Login extends StatefulWidget {
  const Login({super.key});

  @override
  State<Login> createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final TextEditingController _userController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  List<Particle> particles = [];
  final int numberOfParticles = 50;
  late Ticker _ticker;
  bool _isObscure = true;
  bool _isLoading = false; // Para mostrar un indicador de carga
  final AuthService _authService = AuthService(); // Instancia del servicio

  @override
  void initState() {
    super.initState();
    _ticker = Ticker(_tick);

    for (int i = 0; i < numberOfParticles; i++) {
      particles.add(Particle());
    }
    _ticker.start();
  }

  void _tick(Duration elapsed) {
    setState(() {
      for (var particle in particles) {
        particle.x += particle.driftX;
        particle.y += particle.driftY;

        if (particle.x < 0 || particle.x > 1 || particle.y < 0 || particle.y > 1) {
          particle.reset();
        }
      }
    });
  }

  @override
  void dispose() {
    _userController.dispose();
    _passwordController.dispose();
    _ticker.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    if (_isLoading) return;

    setState(() => _isLoading = true);

    try {
      final username = _userController.text.trim();
      final password = _passwordController.text.trim();

      if (username.isEmpty || password.isEmpty) {
        return _showCustomDialog(context, '¡Hubo un error!', 'Por favor, ingresa usuario y contraseña.');
      }

      final response = await _authService.login(username, password, context);

      if (response != null && response.containsKey('error') && response['error']) {
        _showCustomDialog(context, '¡Lo siento!', response['message']);
      } else if (response != null && response.containsKey('token')) {
        await SharedPreferences.getInstance().then((prefs) => prefs.setString('token', response['token']));

        await _showCustomDialog(context, '¡Muy bien!', 'Inicio de sesión exitoso');
        // Navegar después de que el usuario cierre el diálogo
        if (context.mounted) {
          Navigator.of(context).pushNamedAndRemoveUntil('/menu', (route) => false);
        }
      }
    } finally {
      setState(() => _isLoading = false);
    }
  }

  Future<void> _showCustomDialog(BuildContext context, String title, String message) async {
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: Text(message),
          actions: [
            TextButton(
              child: const Text("Aceptar"),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("¡BIENVENIDO A SIMAC!"),
        centerTitle: true,
        titleTextStyle: const TextStyle(
          fontStyle: FontStyle.italic,
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Colors.white,
        ),
        automaticallyImplyLeading: false,
        flexibleSpace: Container(
          decoration: const BoxDecoration(
            gradient: LinearGradient(
              colors: [
                Color(0xFFCDD2C0),
                Color(0xFF70795B),
                Color(0xFF5B6479),
                Color(0xFF765B79),
              ],
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
            ),
          ),
        ),
      ),
      body: Stack(
        children: [
          Container(
            decoration: const BoxDecoration(
              color: Color.fromARGB(255, 30, 36, 16),
            ),
          ),
          CustomPaint(
            foregroundPainter: ParticlePainter(particles: particles),
            size: Size.infinite,
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Center(
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Image.asset('assets/simac.png', width: 150, height: 150),
                    const SizedBox(height: 20),
                    SvgPicture.network('https://cdn.prod.website-files.com/66d75f3897a0c14fc42017af/66fb53bb333d4f968c3acaf8_GM_ESCUDO_MORELOS.svg', height: 80),
                    const SizedBox(height: 30),
                    TextFormField(
                      controller: _userController,
                      decoration: InputDecoration(
                        hintText: "Ingresa tu usuario",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                        ),
                        prefixIcon: const Icon(Icons.person),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 15),
                    TextFormField(
                      controller: _passwordController,
                      obscureText: _isObscure,
                      decoration: InputDecoration(
                        hintText: "Ingresa tu contraseña",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                        ),
                        prefixIcon: IconButton(
                          onPressed: () {
                            setState(() {
                              _isObscure = !_isObscure;
                            });
                          },
                          icon: Icon(
                            _isObscure ? Icons.lock : Icons.lock_open,
                          ),
                        ),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 30),
                    ElevatedButton(
                      onPressed: _isLoading ? null : _login, // Deshabilita el botón mientras carga
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF5B6479),
                        padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 15),
                        textStyle: const TextStyle(fontSize: 18),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(25),
                        ),
                      ),
                      child: _isLoading
                          ? const CircularProgressIndicator(color: Colors.white) // Muestra el indicador de carga
                          : const Row(
                              mainAxisSize: MainAxisSize.min,
                              children: [
                                Icon(Icons.login, color: Colors.white),
                                SizedBox(width: 8),
                                Text("Iniciar Sesión", style: TextStyle(color: Colors.white)),
                              ],
                            ),
                    ),
                    const SizedBox(height: 32),
                    Align(
                      alignment: Alignment.center,
                      child: InkWell(
                        onTap: () => Navigator.pushNamed(context, '/register'),
                        child: const Text(
                          '¿Eres nuevo? Registrate',
                          style: TextStyle(
                            color: Colors.white,
                            decoration: TextDecoration.underline,
                            decorationColor: Colors.white30,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}