Proceso terreno_o_automovil
	Definir precio, incremento,d como real;
	Escribir "Ingresa el precio del terreno y automóvil";
	leer precio;
	Escribir "La devaluación anual del automovil es de 10%";
	Escribir "Ingresa el incremento anual del terreno %";
	leer i;
	i <-((precio * i) / 100) * 3/ 2;
	d <-((precio * 10) / 100) * 3;
	Escribir "La mitad del incremento de la casa en 3 años es:$",i;
	Escribir "La devaluación del automóvil en 3 años es: $",d;
	Si d< i Entonces;
		Escribir "Te conviene comprar el Automovil";
		
	SiNo
		Escribir "Te conviene comprar el terreno";
		
	FinSi
FinProceso
