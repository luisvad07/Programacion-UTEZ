Proceso servicio_militar
	Definir edad, anio_nac , anio_actual Como Entero;
	Definir sexo Como Caracter;
	Escribir "Introduce tu año de nacimiento: ";
	Leer anio_nac;
	Escribir "Introduce el año actual: ";
	Leer anio_actual;
	Escribir "Introduce el genero de la persona (Hombre/Masculino) o (Mujer/Femenino" ;
	Leer sexo;
	edad <- anio_actual - anio_nac;
	Escribir "Tienes ", edad, " años";
	Esperar 1 Segundos;
	Si (edad>=18) y (sexo=="Hombre" o sexo=="Masculino") Entonces
		Escribir "Debes hacer el Servicio Militar Obligatoriamente";
	Sino 
		Escribir "Ya sea que eres menor de edad y/o eres mujer, no lo puedes hacer";
	FinSi
	
FinProceso
