Proceso operaciones_logicas
	Definir opera_logica,function Como Caracter;
	Escribir "Ingresa la función logica en Mayusculas (AND/OR)";
	Leer opera_logica;
	Si opera_logica="AND" Entonces
		Escribir "Ingresa en Mayusculas tu operación en letras (Ejemplo=VF)";
		Escribir "Verdadero/Falso";
		Leer function; 
		Segun function Hacer
			Caso "VV":
				Escribir "Verdadero";
			Caso "VF":
				Escribir "Falso";
			Caso "VF":
				Escribir "Falso";
			Caso "FF":
				Escribir "Falso";
		FinSegun
	SiNo
		Si opera_logica="OR" Entonces
			Escribir "Ingresa en Mayusculas tu operación en letras (Ejemplo=VF)";
			Escribir "Verdadero/Falso";
			Leer function;
			Segun function Hacer
				Caso "VV":
					Escribir "Verdadero";
				Caso "VF":
					Escribir "Verdadero";
				Caso "FV":
					Escribir "Verdadero";
				Caso "FF":
					Escribir "Falso";
			FinSegun
		FinSi
	FinSi
FinProceso
