Proceso operaciones_aritmeticas
	Definir a, b, result Como Real;
	Definir num Como Entero;
	Escribir "Ingresa el primer numero:";
	Leer a;
	Escribir "Ingresa el segundo numero:";
	Leer b;
	Escribir "Introduce el numero de la operacion que desea efectuar: 1- suma, 2- resta, 3-multiplicación, 4-división, 5-potencia y 6-raíz n-ésima";
	Leer num;
		Segun num hacer
			Caso 1:
				result=a+b;
				Escribir "El resultado de la suma es: ",result;
				Escribir "Se realizo la suma correctamente";
			Caso 2:
				result=a-b;
				Escribir "El resultado de la resta es: ",result;
				Escribir "Se realizo la resta correctamente";
			Caso 3:
				result=a*b;
				Escribir "El resultado de la multiplicacion es: ",result;
				Escribir "Se realizo la multiplicacion correctamente";
			Caso 4:
				result=a/b;
				Escribir "El resultado de la division es: ",result;
				Escribir "Se realizo la division correctamente";		
			Caso 5:
				result=a^b;
				Escribir "El resultado de la potencia es: ",result;
				Escribir "Se realizo la potencia correctamente";
			Caso 6:
				result= raiz(a)^b;
				Escribir "El resultado de la raiz n-esima es: ",result;
				Escribir "Se realizo la suma correctamente";		
		De Otro Modo
			Escribir "SYNTAX ERROR";
	FinSegun
FinProceso
