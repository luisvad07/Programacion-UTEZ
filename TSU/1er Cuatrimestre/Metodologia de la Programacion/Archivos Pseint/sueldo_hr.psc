Proceso sueldo_hr
	Definir horas, sueldo, pago, triples, dobles Como Real;
	Escribir "Ingrese las horas trabajadas: ";
	Leer horas;
	Escribir "Ingrese el pago por hora: ";
	Leer pago;
	Si horas > 48 Entonces
		triples <- horas - 48;
		sueldo <- 40*pago + (8*pago*2) + (triples*pago*3);
	Sino	
	Si horas > 40 Entonces
		dobles <- horas - 40;
		sueldo <- 40*pago + (dobles*pago*2);
	SiNo
		sueldo <- horas*pago;
	FinSi
	FinSi
	Escribir "El pago semanal de la persona es de: ", sueldo , " y las horas trabajadas fueron de ", horas;
FinProceso
