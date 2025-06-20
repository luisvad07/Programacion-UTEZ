Proceso Boletos
	Definir compra, total, subtotal Como Real;
	Definir edad Como Entero;
	Definir tarje Como Caracter;
	Escribir "Ingresa el precio del boleto: ";
	Leer compra;
	Escribir "Ingresa la edad de la persona: ";
	Leer edad;
	Si edad>65 Entonces
		Escribir "Usted es Adulto Mayor";
		Escribir "¿Presenta su tarjeta del INAPAM?";
		Leer tarje;
		Si tarje=="si" Entonces
			Escribir "Se aplica el descuento";
			subtotal<-compra*0.50;
			total<-compra-subtotal;
			Escribir "Tu total con el descuento es de: ", total;
		SiNo
			Escribir "No se te aplica el descuento porque no tienes tu tarjeta";
			Escribir "Tu total es de: ", compra;
		FinSi
	SiNo
		Escribir "No se te aplica el descuento porque no eres mayor de edad";
		Escribir "Tu total es de: ", compra;
	FinSi
FinProceso
