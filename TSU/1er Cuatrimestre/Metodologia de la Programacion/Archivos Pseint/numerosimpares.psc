Proceso numerosimpares
	Definir cont, n , acu Como Entero;
	Escribir "Ingrese un numero: ";
	Leer n;
	cont <- 0;
	acu <- 0;
	Repetir
		Si cont MOD 2==1 Entonces
			Escribir cont;
			acu <- acu + 1;
		FinSi
		cont <- cont+1;
	Hasta Que cont >= n
	Escribir "La cantidad de numeros impares es de: ", acu;
FinProceso
