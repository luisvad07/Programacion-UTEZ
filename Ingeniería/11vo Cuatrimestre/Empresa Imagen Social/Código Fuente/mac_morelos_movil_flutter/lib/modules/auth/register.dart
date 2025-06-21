import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_svg/svg.dart';
import 'package:mac_morelos_movil_flutter/kernel/services/auth_service.dart';
import 'package:mac_morelos_movil_flutter/modules/widgets/particle_painter.dart';

class Register extends StatefulWidget {
  const Register({super.key}); // Added Key? key
  @override
  State<Register> createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  final TextEditingController _firstNameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _phoneController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _rfcController = TextEditingController();
  final TextEditingController _curpController = TextEditingController();
  final TextEditingController _birthdateController = TextEditingController();
  final TextEditingController _streetController = TextEditingController();
  final TextEditingController _numberController = TextEditingController();
  final TextEditingController _interiorNumberController = TextEditingController();
  final TextEditingController _neighborhoodController = TextEditingController();
  final TextEditingController _zipCodeController = TextEditingController();
  final TextEditingController _cityController = TextEditingController();
  final TextEditingController _stateController = TextEditingController();
  final TextEditingController _countryController = TextEditingController();
  final TextEditingController _latitudeController = TextEditingController();
  final TextEditingController _longitudeController = TextEditingController();
  final TextEditingController _usernameController = TextEditingController();

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
    _firstNameController.dispose();
    _lastNameController.dispose();
    _emailController.dispose();
    _phoneController.dispose();
    _passwordController.dispose();
    _rfcController.dispose();
    _curpController.dispose();
    _birthdateController.dispose();
    _streetController.dispose();
    _numberController.dispose();
    _interiorNumberController.dispose();
    _neighborhoodController.dispose();
    _zipCodeController.dispose();
    _cityController.dispose();
    _stateController.dispose();
    _countryController.dispose();
    _latitudeController.dispose();
    _longitudeController.dispose();
    _usernameController.dispose();

    _ticker.dispose();
    super.dispose();
  }

  Future<void> _register() async {
    if (_isLoading) return;

    setState(() => _isLoading = true);

    try {
      // Collect data from controllers
      final userData = {
        "role": {
          "roleId": 1
        }, // Citizen role
        "firstName": _firstNameController.text.trim(),
        "lastName": _lastNameController.text.trim(),
        "email": _emailController.text.trim(),
        "phone": _phoneController.text.trim(),
        "password": _passwordController.text.trim(),
        "rfc": _rfcController.text.trim(),
        "curp": _curpController.text.trim(),
        "birthdate": _birthdateController.text.trim(),
        "address": {
          "street": _streetController.text.trim(),
          "number": _numberController.text.trim(),
          "interiorNumber": _interiorNumberController.text.trim(),
          "neighborhood": _neighborhoodController.text.trim(),
          "zipCode": _zipCodeController.text.trim(),
          "city": _cityController.text.trim(),
          "state": _stateController.text.trim(),
          "country": _countryController.text.trim(),
          "latitude": double.tryParse(_latitudeController.text.trim()) ?? 0.0,
          "longitude": double.tryParse(_longitudeController.text.trim()) ?? 0.0
        },
        "username": _usernameController.text.trim()
      };

      // Validate required fields (add more as needed)
      if (userData["firstName"].toString().isEmpty || // toString() to handle null values
          userData["lastName"].toString().isEmpty ||
          userData["email"].toString().isEmpty ||
          userData["password"].toString().isEmpty ||
          userData["username"].toString().isEmpty) {
        return _showCustomDialog(context, '¡Error!', 'Por favor, complete todos los campos obligatorios.');
      }

      final response = await _authService.registerUser(userData);

      // Asegurar que el error solo se muestra si existe y es verdadero
      if (response == null || (response.containsKey('error') && response['error'] == true)) {
        await _showCustomDialog(context, '¡Error!', response?['message'] ?? 'Ocurrió un error inesperado.');
      } else {
        await _showCustomDialog(context, '¡Éxito!', 'Registro exitoso. Por favor, inicie sesión.');
        if (context.mounted) {
          Navigator.of(context).pushReplacementNamed('/login');
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
        title: const Text("Registro de Usuario"),
        centerTitle: true,
        titleTextStyle: const TextStyle(
          fontStyle: FontStyle.italic,
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Colors.white,
        ),
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
                    SvgPicture.network('https://cdn.prod.website-files.com/66d75f3897a0c14fc42017af/66fb53bb333d4f968c3acaf8_GM_ESCUDO_MORELOS.svg', height: 60),
                    const SizedBox(height: 10),
                    TextFormField(
                      controller: _firstNameController,
                      decoration: InputDecoration(
                        hintText: "Nombre",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.person),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _lastNameController,
                      decoration: InputDecoration(
                        hintText: "Apellido",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.person),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _emailController,
                      decoration: InputDecoration(
                        hintText: "Correo Electrónico",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.email),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _phoneController,
                      decoration: InputDecoration(
                        hintText: "Teléfono",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.phone),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _passwordController,
                      obscureText: _isObscure,
                      decoration: InputDecoration(
                        hintText: "Contraseña",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
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
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _rfcController,
                      decoration: InputDecoration(
                        hintText: "RFC",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.badge),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _curpController,
                      decoration: InputDecoration(
                        hintText: "CURP",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.card_membership),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _birthdateController,
                      readOnly: true, // Para evitar la entrada manual
                      decoration: InputDecoration(
                        hintText: "Fecha de Nacimiento (YYYY-MM-DD)",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.calendar_today),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                      onTap: () async {
                        DateTime? pickedDate = await showDatePicker(
                          context: context,
                          initialDate: DateTime.now(),
                          firstDate: DateTime(1900),
                          lastDate: DateTime.now(),
                        );

                        if (pickedDate != null) {
                          // Formatear la fecha seleccionada
                          String formattedDate = "${pickedDate.year}-${pickedDate.month.toString().padLeft(2, '0')}-${pickedDate.day.toString().padLeft(2, '0')}";

                          // Asignar la fecha al controlador
                          setState(() {
                            _birthdateController.text = formattedDate;
                          });
                        }
                      },
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _streetController,
                      decoration: InputDecoration(
                        hintText: "Calle",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.home),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _numberController,
                      decoration: InputDecoration(
                        hintText: "Número",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_on),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _interiorNumberController,
                      decoration: InputDecoration(
                        hintText: "Número Interior",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_on),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _neighborhoodController,
                      decoration: InputDecoration(
                        hintText: "Colonia",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_on),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _zipCodeController,
                      decoration: InputDecoration(
                        hintText: "Código Postal",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.local_post_office),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _cityController,
                      decoration: InputDecoration(
                        hintText: "Ciudad",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_city),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _stateController,
                      decoration: InputDecoration(
                        hintText: "Estado",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.map),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _countryController,
                      decoration: InputDecoration(
                        hintText: "País",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.flag),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _latitudeController,
                      decoration: InputDecoration(
                        hintText: "Latitud",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_searching),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _longitudeController,
                      decoration: InputDecoration(
                        hintText: "Longitud",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.location_searching),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),
                    const SizedBox(height: 8),
                    TextFormField(
                      controller: _usernameController,
                      decoration: InputDecoration(
                        hintText: "Nombre de Usuario",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        prefixIcon: const Icon(Icons.person),
                        filled: true,
                        fillColor: Colors.white.withOpacity(0.7),
                      ),
                    ),

                    const SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: _isLoading ? null : _register, // Deshabilita el botón mientras carga
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
                          : const Text("Registrarse", style: TextStyle(color: Colors.white)),
                    ),
                    const SizedBox(height: 10),
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).pushReplacementNamed('/login'); // Navigate to login
                      },
                      child: const Text("¿Ya tienes una cuenta? Inicia Sesión", style: TextStyle(color: Colors.white70)),
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