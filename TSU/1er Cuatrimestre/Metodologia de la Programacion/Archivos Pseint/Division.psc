Proceso Division
	Definir num1, num2, result Como Entero;
	Escribir "Ingresa el primer numero:";
	leer num1;
	Escribir "Ingresa el segundo numero:";
	leer num2;
	Esperar 1 Segundos;
	Si num1 MOD num2 == 0 Entonces
		result <- num1/num2;
		Escribir "Los numeros se pueden dividir";
		Esperar 1 Segundos;
		Escribir "El resultado de la division es: ",result;
	SiNo
		Escribir "Los numeros no se pueden dividir, ERROR!";
	FinSi
FinProceso
