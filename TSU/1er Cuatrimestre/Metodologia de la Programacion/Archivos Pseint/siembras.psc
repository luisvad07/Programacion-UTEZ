Proceso siembras
	Definir h, pino, oyamel, cedro, cp, co, cc, sp,mp como real;
	Escribir "Indica el número de hectáreas en metros";
	Escribir "Recuerda: 10000m^2 es igual a una hectárea";
	Leer h;
	Si h> 1000000 entonces
		pino <- h* 0.7;
		oyamel <- h* 0.20;
		cedro <- h* 0.10;
	SiNo 
		Si h <= 1000000 Entonces
			pino <- h*0.5;
			oyamel <- h * 0.3 ;
			cedro <- h*0.2;
		FinSi
	FinSi
	cp <- (pino / 10)*8;
	co <- (oyamel /15)* 15;
	cc <- (cedro / 18) * 10;
	Escribir h, "metros cuadrados";
	Escribir "Según las hectáreas para  pino: ", pino, "en total se sembrarán:", cp;
	Escribir "Según las hectáreas para  oyamel: ", oyamel, "en total se sembrarán:", co;
	Escribir "Según las hectáreas para  cedro: ", pino, "en total se sembrarán:", cc;
FinProceso
