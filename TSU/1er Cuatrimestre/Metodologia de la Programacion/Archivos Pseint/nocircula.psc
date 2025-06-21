Proceso nocircula
	Definir placa Como Entero;
	Escribir "Ingrese el numero de terminacion de placa (Solo un digito, del 0 al 9)";
	Leer placa;
	Esperar 1 Segundos;
	Si placa <=9 entonces
	Segun placa Hacer
		Caso 1,2:
			Escribir "Es lunes y el color de su calcomanía es amarillo, por lo tanto hoy no circula de acuerdo a su informacion";	
		Caso 3,4:
			Escribir "Es martes y el color de su calcomanía es rosa, por lo tanto hoy no circula de acuerdo a su informacion";
		Caso 5,6:
			Escribir "Es miercoles y el color de su calcomanía es rojo, por lo tanto hoy no circula de acuerdo a su informacion";
		Caso 7,8:
			Escribir "Es jueves y el color de su calcomanía es verde, por lo tanto hoy no circula de acuerdo a su informacion";	
		Caso 9,0:
			Escribir "Es viernes y el color de su calcomanía es azul, por lo tanto hoy no circula de acuerdo a su informacion";	
		De Otro Modo:
			Escribir "Como su informacion es diferente, por lo tanto, cualquier dia se circula";
	FinSegun
	FinSi
FinProceso
