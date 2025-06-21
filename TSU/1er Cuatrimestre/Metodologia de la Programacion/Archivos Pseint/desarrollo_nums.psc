Proceso desarrollo_nums
	Definir a,b,result1, result2,result3 Como Entero;
	Escribir "Ingresa el Primer Numero:";
	leer a;
	Escribir "Ingresa el Segundo Numero:";
	leer b;
	Si a=b Entonces
		Escribir "Los numeros ingresados son iguales";
		result1<- a*b;
		Escribir "La multiplicacion es:" result1;
	SiNo
		Si a>b Entonces
			Escribir "El primer numero es mayor que el segundo numero";
			result2 <-a-b;
			Escribir "La resta es : " result2;
		Sino
			Escribir "El primer numero es menor que el segundo numero";
			result3 <-a+b;
			Escribir "La suma es :" result3;
		FinSi
	FinSi
FinProceso
