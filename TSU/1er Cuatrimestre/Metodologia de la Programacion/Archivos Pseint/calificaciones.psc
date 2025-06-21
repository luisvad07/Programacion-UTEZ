Proceso calificaciones
	Definir a,b,c, promedio Como Real;
	Escribir "Ingrese la primera calificacion:";
	Leer a;
	Escribir "Ingrese la segunda calificacion:";
	Leer b;
	Escribir "Ingrese la tercera calificacion:";
	Leer c;
	promedio <- (a+b+c)/3;
	Escribir "El promedio es: ", promedio;
	Esperar 1 Segundos;
	Si promedio>=8.5 Entonces
		Escribir "Felicidades, has obtenido una beca academica";
	FinSi
FinProceso
