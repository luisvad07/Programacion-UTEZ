Proceso produccion
	Definir numprod Como Entero;
	Definir prom Como Real;
	Escribir "Ingrese el número de producción del trabajador: ";
	Leer numprod;
	prom <- numprod/6;
	Si prom >= 100  Entonces
		Escribir "El trabajador recibira incentivos";
	FinSi	
FinProceso
