Proceso alumno
	Definir nombre, grupo Como Caracter;
	Definir grado Como Entero;
	Definir promedio Como Real;
	Escribir "Introduce tu nombre completo: ";
	Leer nombre;
	Escribir "Introduce el grado que estas cursando (De 1ro a 6to): ";
	Leer grado;
	Escribir "Introduce el grupo en el que cursas (De la A a la J, en mayusculas): " ;
	Leer grupo;
    Escribir "Introduce el promedio obtenido: ";
	Leer promedio;
	Esperar 1 Segundos;
	Escribir "Tu nombre completo es: ",nombre;
	Esperar 3 Segundos;
	Si (grado>=1 y grado<=5) Entonces
		Escribir "Estas en la escuela, cursando el ",grado, " grado";
	Sino 
		Escribir "Estas en estadia, cursando el ",grado, " grado";
	FinSi
	Esperar 3 Segundos;
	Si (grupo>="A" y grupo<="E") Entonces
		Escribir "Estas en el turno matutino, en el grupo ",grupo;
	Sino 
		Escribir "Estas en el turno vespertino, en el grupo ",grupo;
	FinSi
	Esperar 3 Segundos;
	Si (promedio>=7 y promedio<=10) Entonces
		Escribir "Estas aprobado, obtiendo ",promedio;
	Sino 
		Escribir "Estas reprobado, obtiendo ",promedio;
	FinSi
	Esperar 1 Segundos;
FinProceso
