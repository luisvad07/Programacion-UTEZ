Proceso ahorro
	Definir mes,interes,dinero,result, final Como Real;
	Escribir "Se determina cu�nto se tiene ahorrado despu�s de 3 a�os si se depositan $10,000.00 mensuales a una tasa de inter�s del 3% mensual";
	Esperar 1 Segundos; 
	
	Escribir "Se multiplica 0.03*36 porque son los 36 meses de 3 a�os";
	Esperar 1 Segundos;
	interes <- 0.03*36;
	Escribir "El resultado del interes es de: ", interes;
	
	Escribir "Despu�s se multiplica 100,000 por el inter�s del porcentaje";
	Esperar 1 Segundos;
	result <- 10000*interes;
	Escribir "El resultado momentaneo es de: ", result;
	
	Escribir "Y el resultado anterior lo multiplicas por 100";
	Esperar 1 Segundos;
	dinero <- result*100;
	Escribir "El resultado del dinero es de: ", dinero;
	
	Escribir "Para finalizar se resta 100,000 menos el interes";
	Esperar 1 Segundos;
	final <- dinero-10000;
	Escribir "El resultado del ahorrado despu�s de 3 a�os es de: ", final;
	
FinProceso
