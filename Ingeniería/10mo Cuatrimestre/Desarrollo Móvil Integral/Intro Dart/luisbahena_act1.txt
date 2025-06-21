//Hecho por Luis Eduardo Bahena Castillo del 10°CIDGS

// Definición de la clase Estudiante
class Estudiante {
  String _nombreEstudiante;
  int _edadEstudiante;
  Map<String, double> _notasEstudiantes;

  // Constructor clase Estudiantes
  Estudiante(
      this._nombreEstudiante, this._edadEstudiante, this._notasEstudiantes);

  // Getters
  String get nombreEstudiante => _nombreEstudiante;
  int get edadEstudiante => _edadEstudiante;
  Map<String, double> get notasEstudiantes => _notasEstudiantes;

  // Setters con validaciones simples
  set nombreEstudiante(String nombreEstudiante) {
    if (nombreEstudiante.isNotEmpty) {
      this._nombreEstudiante = nombreEstudiante;
    } else {
      print('¡Este campo no puede ser vacío!');
    }
  }

  set edadEstudiante(int edadEstudiante) {
    if (edadEstudiante > 0) {
      this._edadEstudiante = edadEstudiante;
    } else {
      print('¡La edad debe ser mayor que 0!');
    }
  }

  // Modifica la nota de un estudiante con validación
  void setNotaEstudiante(String nombre, double nota) {
    if (nota >= 0.0 && nota <= 10.0) {
      _notasEstudiantes[nombre] = nota;
    } else {
      print('La nota debe estar entre 0.0 y 10.0');
    }
  }

  // Obtiene la nota de una materia, devolviendo -1 si no existe
  double getNotaEstudiante(String nombre) {
    return _notasEstudiantes.containsKey(nombre)
        ? _notasEstudiantes[nombre]!
        : -1; // Retorna -1 si la materia no existe
  }

  // Calcula el promedio de las notas
  double calcularPromedio() {
    if (_notasEstudiantes.isNotEmpty) {
      double sum = _notasEstudiantes.values.reduce((a, b) => a + b);
      return sum / _notasEstudiantes.length;
    } else {
      print('¡La lista de calificaciones está vacía!');
    }
    return 0.0; // Si no hay notas, el promedio es 0
  }

  // Muestra detalles del estudiante
  void mostrarDetalles() {
    print('Nombre: $_nombreEstudiante, Edad: $_edadEstudiante');
    print('Notas:');
    _notasEstudiantes.forEach((asignatura, nota) {
      print('$asignatura: $nota');
    });
  }
}

// Definición de la clase Curso
class Curso {
  List<Estudiante> _estudiantes = [];

  // Agrega a un  estudiante al curso
  void agregarEstudiante(Estudiante estudiante) {
    _estudiantes.add(estudiante);
    print('${estudiante.nombreEstudiante} ha sido agregado al curso.');
  }

  // Elimina a un estudiante del curso
  void eliminarEstudiante(String nombre) {
    // Contar la cantidad de estudiantes antes de eliminar
    int cantidadAntes = _estudiantes.length;

    // Eliminar estudiantes que coincidan con el nombre
    _estudiantes.removeWhere((est) => est.nombreEstudiante == nombre);

    // Contar la cantidad de estudiantes después de eliminar
    int cantidadDespues = _estudiantes.length;

    // Verificar si se eliminó algún estudiante
    if (cantidadAntes > cantidadDespues) {
      print('Estudiante $nombre ha sido eliminado del curso.');
    } else {
      print('El estudiante $nombre no existe en el curso.');
    }
  }

  // Muestra a todos los estudiantes existentes
  void mostrarEstudiantes() {
    if (_estudiantes.isEmpty) {
      print('No hay ningún estudiante en el curso.');
    } else {
      _estudiantes.forEach((estudiante) {
        estudiante.mostrarDetalles();
        print('Promedio: ${estudiante.calcularPromedio()}\n');
      });
    }
  }

  // Calcula el promedio del curso
  double calcularPromedioCurso() {
    if (_estudiantes.isEmpty)
      return 0.0; //Asegura primero que si esta vacío siempre devolverá 0.0

    double totalPromedios = 0.0;
    _estudiantes.forEach((estudiante) {
      totalPromedios += estudiante.calcularPromedio();
    });

    return totalPromedios / _estudiantes.length;
  }
}

void main() {
  Curso curso = Curso();

  //Simulación Correcta

  // Simula agregar a 3 estudiantes en el sistema
  curso.agregarEstudiante(
      Estudiante('Melissa Rojas', 20, {'Español': 9.0, 'Matemáticas': 7.5}));
  curso.agregarEstudiante(
      Estudiante('Ulises Solano', 22, {'Español': 8.0, 'Matemáticas': 9.5}));
  curso.agregarEstudiante(
      Estudiante('Luis Eduardo', 21, {'Español': 9.0, 'Matemáticas': 9.2}));

  // Muestra a todos los estudiantes existentes
  curso.mostrarEstudiantes();

  // Elimina a un estudiante existente
  curso.eliminarEstudiante('Ulises Solano');

  // Muestra a los estudiantes después de la eliminación
  curso.mostrarEstudiantes();

  // Muestra el promedio del curso
  double promedioCurso = curso.calcularPromedioCurso();
  print('El promedio del curso es: $promedioCurso');



  //Simulación de Errores

  // Agrega a un estudiante
  Estudiante estudianteC =
      Estudiante('Roberto Gómez', 18, {'Historia': 6.0, 'Geografía': 8.0});
  curso.agregarEstudiante(estudianteC);

  // Simula agregar a un estudiante inválido con nombre vacío y edad no válida
  Estudiante estudianteInvalido = Estudiante('', -5, {'Matemáticas': 7.0});
  estudianteInvalido.nombreEstudiante = '';
  estudianteInvalido.edadEstudiante = -3;

  // Simula asignae una nota fuera del rango a un estudiante
  estudianteInvalido.setNotaEstudiante('Matemáticas', 11.0);

  // Simula obtener una nota del Estudiante Roberto Gómez de una materia inexistente
  double notaQuimica = estudianteC.getNotaEstudiante('Química');
  if (notaQuimica == -1) {
    print('El estudiante no tiene calificación en Química.');
  } else {
    print('Nota en Química: $notaQuimica');
  }

  // Simula eliminar a un estudiante que no existe
  curso.eliminarEstudiante('Chabelo Martínez');

  // Simula mostrar estudiantes en un curso vacío
  Curso cursoVacio = Curso();
  cursoVacio.mostrarEstudiantes();
}
