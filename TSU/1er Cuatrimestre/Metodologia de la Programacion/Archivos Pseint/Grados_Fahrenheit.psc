Proceso Grados_Fahrenheit
	Definir temperatura, f Como Real;
	Escribir "Ingresa la temperatura actual en grados Celsius:";
	Leer temperatura;
	f <- (temperatura*1.8)+32;
	Esperar 1 Segundos;
	Si f>= 85 Entonces
		Escribir "La Natación es el deporte apropiado para practicar a esa temperatura";
		Escribir "La temperatura actual es de ", temperatura, " grados celsius";
	SiNo
		Esperar 1 Segundos;
		Si f>=70 Entonces
			Escribir "El Tennis es el deporte apropiado para practicar a esa temperatura";
			Escribir "La temperatura actual es de ", temperatura ," grados celsius";
		SiNo
			Esperar 1 Segundos;
			Si f>=32 Entonces
				Escribir "El Golf es el deporte apropiado para practicar a esa temperatura";
				Escribir "La temperatura actual es de ", temperatura, " grados celsius";
			SiNo
				Esperar 1 Segundos;
				Si f>=10 Entonces
					Escribir "El Esquí es el deporte apropiado para practicar a esa temperatura";
					Escribir "La temperatura actual es de ", temperatura, " grados celsius";
				SiNo
					Esperar 1 Segundos;
					Si f<=10 Entonces
						Escribir "La Marcha es el deporte apropiado para practicar a esa temperatura";
						Escribir "La temperatura actual es de ", temperatura, " grados celsius";
					FinSi
				FinSi
			FinSi
		FinSi
		
	FinSi
FinProceso