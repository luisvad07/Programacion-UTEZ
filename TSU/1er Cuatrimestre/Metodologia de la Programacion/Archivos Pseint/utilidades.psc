Proceso utilidades
	Definir anem Como entero ;
	Definir sal, util como real;
	Escribir "Indica los años que lleva en la empresa";
	Leer anem;
	Escribir "Indica el salario mensual";
	Leer sal;
	Si anem < 1 Entonces
		util <- sal * 0.05;
	SiNo 
		Si anem >= 1 & anem < 2 Entonces
			util <- sal * 0.07;
		SiNo
			Si anem >= 2 & anem <5 Entonces
				util <- sal* 0.10;
			SiNo 
				Si anem >=5 & anem < 10 Entonces
					util <- sal*0.15;
				SiNo 
					Si util >= 10 Entonces
						util <- sal*0.20;
					FinSi
				FinSi
			FinSi
		FinSi
	FinSi
	Escribir "La utilidad del reparto anual es:", util;
FinProceso
