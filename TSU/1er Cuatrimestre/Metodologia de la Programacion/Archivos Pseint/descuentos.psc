Proceso descuentos
	Definir compra, descu, color Como Real;
	Escribir "Ingresa el total de la compra realizada";
	Leer compra;
	Escribir "De acuerdo a estas opciones selecciona una bola";
	Escribir "1. Verde";
	Escribir "2. Amarillo";
	Escribir "3. Rojo";
	Escribir "4. Blanco";
	Leer color;
	Esperar 1 Segundos;
	Segun color Hacer
		Caso 1:
			descu = compra * .30;
		Caso 2:
			descu = compra * .20;
		Caso 3:
			descu = compra * .10;
		Caso 4:
			descu = 0;	
		De Otro Modo:
			Escribir "El color de bola no existe";
			
	Fin Segun
	Esperar 1 Segundos;
		Escribir "El total a pagar ya con el descuento aplicado es: $",compra - descu;
		Escribir "El descuento aplicado es: $",descu;
FinProceso
