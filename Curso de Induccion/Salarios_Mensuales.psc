// Realizar el pseudocódigo que resuelva el siguiente problema. Calcular el salario semanal de un empleado, 
//sabiendo que salario se calcula con base a las horas semanales trabajadas y de acuerdo a un precio especificadopor horas. 
//Si el empleado trabaja mas de cuarenta horas en la semana, las horas extras se pagarán  a razón de 1.5 veces la hora ordinaria
Proceso Salarios_Mensuales
	Definir hr Como Entero;
	Definir pago_hr, total1, total2 Como Real;
	Escribir "Ingresa las horas trabajadas de la semana: ";
	Leer hr;
	Escribir "Ingresa el pago por hora de la semana: ";
	Leer pago_hr;
	Si hr>40 Entonces
		total1 <- hr*pago_hr;
		total2 <- total1*1.5;
		Escribir "El total de la semana trabajada con las horas extras es de: ",total2;
	SiNo
		total1 <- hr*pago_hr;
		Escribir "El total de la semana trabajada es de: ",total1;
	FinSi
FinProceso
