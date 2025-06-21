Proceso tabla_de_multiplicar_ingreso
	Definir cont, num, result como entero;
	Escribir "Ingresa un numero para verificar la tabla de multiplicar: ";
	Leer num;
	cont<-1;
	Mientras (contador<=10) Hacer
		result<-num*contador;
		Escribir num,"*",cont,"=",result;
		cont<-cont+1;
	FinMientras	
FinProceso
