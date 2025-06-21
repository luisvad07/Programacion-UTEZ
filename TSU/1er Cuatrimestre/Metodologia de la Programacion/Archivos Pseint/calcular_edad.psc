Proceso calcular_edad
	Definir anio, mes, edad Como Entero;
	Escribir "Una persona nacio en Octubre de 1978";
	Escribir "Escribe el año en el que esta: ";
	Leer anio;
	Escribir "Escribe el numero del mes en el que esta: ";
	Leer mes;
	edad <- (((anio-1)-1978)*12)+(mes+2);
	Escribir "Su edad es de: ", edad , " meses.";
FinProceso
