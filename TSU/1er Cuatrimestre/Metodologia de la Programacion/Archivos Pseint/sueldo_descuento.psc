Proceso sueldo_descuento
	Definir horas, sueldo, pago, extra Como Real;
	Escribir "Ingrese las horas trabajadas: ";
	Leer horas;
	Escribir "Ingrese el pago por hora: ";
	Leer pago;
	Si horas > 45 Entonces
		extra <- ((horas-45) * (pago*1.5));
		salario <- (extras + (pago*45));
	SiNo
		salario <- horas*pago;
	FinSi
	Escribir "El pago semanal de la persona es de: ", salario;
FinProceso
