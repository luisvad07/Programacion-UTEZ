Proceso par_impar
	Definir a,b,contpar,contimpar Como Entero;
	contpar <- 0;
	contimpar <- 0;
	Escribir "num de inicio: 300";
	Esperar 1 Segundos;
	a <- 300;
	Escribir "num final: 450";
	Esperar 1 Segundos;
	b <- 450;
	Mientras a<b Hacer
		Si a MOD 2==0 Entonces
			contpar <- contpar+a;
		SiNo
			contimpar <- contimpar+a;
		FinSi
		a <- a+1;
	FinMientras
	Escribir "La suma de los pares es de: ", contpar;
	Escribir "La suma de los impares es de: ", contimpar;
FinProceso
