Algoritmo Examenes
	Definir a, b, c, d Como Real;
	Definir ponderacion, result Como Real;
	Escribir "Ingrese las calificaciones de los 4 examenes";
	Escribir "Examen 1";
	Leer a;
	Escribir "Examen 2";
	Leer b;
	Escribir "Examen 3";
	Leer c;
	Escribir "Examen 4";
	Leer d;
	ponderacion <- 2.5*4;
	result <- ((a*2.5)+(b*2.5)+(c*2.5)+(d*2.5)) / ponderacion;
	Escribir "El promedio total es de: ", result;
FinAlgoritmo
