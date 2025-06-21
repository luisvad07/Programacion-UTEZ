import 'dart:math';
import 'package:flutter/material.dart';

class Particle {
  double x;
  double y;
  double size;
  Color color;
  double driftX; // Movimiento horizontal sutil
  double driftY; // Movimiento vertical sutil

  Particle()
      : x = Random().nextDouble(), // Posición horizontal aleatoria
        y = Random().nextDouble(), // Posición vertical aleatoria
        size = Random().nextDouble() * 3 + 1, // Tamaño pequeño
        color = Colors.white.withOpacity(Random().nextDouble() * 0.2), // Opacidad sutil
        driftX = (Random().nextDouble() - 0.5) * 0.001, // Movimiento horizontal aleatorio y lento
        driftY = (Random().nextDouble() - 0.5) * 0.001; // Movimiento vertical aleatorio y lento

  void reset() {
    x = Random().nextDouble();
    y = Random().nextDouble();
    driftX = (Random().nextDouble() - 0.5) * 0.001;
    driftY = (Random().nextDouble() - 0.5) * 0.001;
  }
}

class ParticlePainter extends CustomPainter {
  final List<Particle> particles;

  ParticlePainter({required this.particles}) : super(repaint: null);

  @override
  void paint(Canvas canvas, Size size) {
    for (var particle in particles) {
      final Offset offset = Offset(particle.x * size.width, particle.y * size.height);
      canvas.drawCircle(offset, particle.size, Paint()..color = particle.color);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }
}