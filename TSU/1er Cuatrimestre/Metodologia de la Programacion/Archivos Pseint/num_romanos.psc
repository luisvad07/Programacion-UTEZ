Proceso num_romanos
	Definir unidad,num Como Entero;
	Escribir "Ingrese un numero del uno al diez";
	leer num;
	unidad = trunc(num/1) mod 10;
	Segun unidad hacer
		Caso 1:
			Escribir "El numero ", num, " en romano es I";
		Caso 2:
			Escribir "El numero ", num, " en romano es II";
		Caso 3:
			Escribir "El numero ", num, " en romano es III";
		Caso 4:
			Escribir "El numero ", num, " en romano es IV";
		Caso 5:
			Escribir "El numero ", num, " en romano es V";
		Caso 6:
			Escribir "El numero ", num, " en romano es VI";
		Caso 7:
			Escribir "El numero ", num, " en romano es VII";
		Caso 8:
			Escribir "El numero ", num, " en romano es VIII";
		Caso 9:
			Escribir "El numero ", num, " en romano es IX";
		Caso 10:
			Escribir "El numero ", num, " en romano es X";
		De Otro Modo:
			Escribir "Error en la conversion";
	FinSegun
FinProceso
