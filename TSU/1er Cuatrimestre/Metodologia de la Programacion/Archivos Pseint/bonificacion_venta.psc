Proceso bonificacion_venta
	Definir mont, total Como Real;
	Escribir "Ingresa el monto total de ventas del mes:";
	Leer mont;
	Si mont<=1000 entonces
		total <- mont*.0;
		Escribir "La bonificación total de ventas es de: ",total;
	Sino 
		Si mont <=5000 entonces
			total <- mont*.05;
			Escribir "La bonificación total de ventas es de: ",total;
		SiNo 
			Si mont <=10000 entonces
				total <- mont*.10;
				Escribir "La bonificación total de ventas es de: ",total;
			SiNo
				Si mont >= 10001 entonces
					total <- mont*.15;
					Escribir "La bonificación total de ventas es de: ",total;
				FinSi
			FinSi
		FinSi
	FinSi
FinProceso