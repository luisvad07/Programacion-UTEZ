Proceso opciones_descuento
	Definir compra, total, subtotal Como Real;
	Definir opcion Como Entero;
	Definir respuesta Como Caracter;
	Escribir "Ingresa el precio del boleto: ";
	Leer compra;
	Escribir "Ingresa la opcion del descuento que desea efectuar:";
	Escribir "1. Pasajero normal: 0% , 2. Maestro en periodo vacacional: 25% , 3. Menores de edad: 40% , 4. Adultos mayores: 50%";
	Leer opcion;
	Segun opcion Hacer
		1:
			Escribir "No se te aplica el descuento porque eres pasajero normal";
			Escribir "Tu total es de: ", compra;
		2:
			Escribir "¿Eres maestro en periodo vacacional?";
			Leer repuesta;
			Si respuesta=="Si" o respuesta=="si" Entonces
				Escribir "Se aplica el descuento";
				subtotal<-compra*0.25;
				total<-compra-subtotal;
				Escribir "Tu total con el descuento es de: ", total;
			SiNo
				Escribir "No se te aplica el descuento porque eres maestro en periodo escolar";
				Escribir "Tu total es de: ", compra;
			FinSi
	    3:
			Escribir "¿Eres Menor de edad?";
			Leer repuesta;
			Si respuesta=="Si" o respuesta=="si" Entonces
				Escribir "Se aplica el descuento";
				subtotal<-compra*0.40;
				total<-compra-subtotal;
				Escribir "Tu total con el descuento es de: ", total;
			SiNo
				Escribir "No se te aplica el descuento porque eres mayor de edad";
				Escribir "Tu total es de: ", compra;
			FinSi
	    4:
			Escribir "¿Eres Adulto mayor?";
			Leer repuesta;
			Si respuesta=="Si" o respuesta=="si" Entonces
				Escribir "Se aplica el descuento";
				subtotal<-compra*0.25;
				total<-compra-subtotal;
				Escribir "Tu total con el descuento es de: ", total;
			SiNo
				Escribir "No se te aplica el descuento porque no eres Adulto mayor";
				Escribir "Tu total es de: ", compra;
			FinSi
		De Otro Modo
			Escribir "Error! opcion no valida!";	
	FinSegun
FinProceso
	