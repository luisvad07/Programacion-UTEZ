Proceso Menu_Figuras3D
	Definir Vol,Area_T,Area_B,Area_L,Per_B,AF,h,ap,app,a,b,d,n,l,r Como Real;
	Definir num Como Entero;
	Escribir "Bienvenido al menu de Figuras 3D";
	Escribir "--------------------------------";
	Escribir "Introduce el numero del problema que desea efectuar:";
	Escribir "1) CUBO";
	Escribir "2) PRISMA HEXAGONAL";
	Escribir "3) PRISMA TRIANGULAR";
	Escribir "4) PRISMA PENTAGONAL";    
	Escribir "5) CILINDRO";
	Escribir "6) PIRÁMIDE TRIANGULAR";
	Escribir "7) PIRÁMIDE CUADRANGULAR";
	Escribir "8) PIRÁMIDE RECTANGULAR";
	Escribir "9) PIRÁMIDE PENTAGONAL";
	Escribir "10) PIRÁMIDE HEXAGONAL";
	Leer num;
	Segun num hacer
		
		Caso 1: // Opcion Cubo
			Escribir "Ingresa la medida del lado o arista";
			Leer l;
			Escribir "Calculando el volumen del Cubo....";
			Esperar 5 segundos;
			Vol=l^3; //Formula Volumen Cubo
			Escribir "El volumen del Cubo es: ",Vol;
			Escribir "Calculando el area del Cubo....";
			Esperar 5 segundos;
			Area_T=6*(l^2); //Formula Area Cubo
			Escribir "El area total del Cubo es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 2: // Opcion Prisma Hexagonal
			Escribir "Ingresa la medida de la altura del prisma";
			Leer h;
			Escribir "Ingresa la medida del lado o arista";
			Leer l;
			Escribir "Ingresa el apotema del Hexagono";
			Leer ap;
			Escribir "Calculando el perimetro del Hexagono....";
			Esperar 5 segundos;
			Per_B=6*l; //Formula Perimetro Hexagono
			Escribir "El perimetro del Hexagono es: ",Per_B;
			Escribir "Calculando el area del Hexagono....";
			Esperar 5 segundos;
			Area_B=(Per_B*ap)/2; //Formula Area Hexagono
			Escribir "El Area del Hexagono es: ",Area_B;
			Escribir "Calculando el volumen del Prisma Hexagonal....";
			Esperar 5 segundos;
			Vol=Area_B*h; //Formula Volumen Prisma Hexagonal
			Escribir "El volumen del Prisma Hexagonal es: ",Vol;
			Escribir "Calculando el area lateral del Prisma Hexagonal....";
			Esperar 5 segundos;
			Area_L=Per_B*h; //Formula Area Lateral Prisma Hexagonal
			Escribir "El area lateral del Prisma Hexagonal es: ",Area_L;
			Escribir "Calculando el area total del Prisma Hexagonal....";
			Esperar 5 segundos;
			Area_T=(2*Area_B)+Area_L; //Formula Area Prisma Hexagonal
			Escribir "El area total del Prisma Hexagonal es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 3: //Opcion Prisma Triangular
			Escribir "Ingresa la medida de la altura del prisma";
			Leer h;
			Escribir "Ingresa la medida del lado del Triangulo";
			Leer b;
			Escribir "Calculando el perimetro del Triangulo....";
			Esperar 5 segundos;
			Per_B=3*b; //Formula Perimetro Triangulo
			Escribir "El perimetro del Triangulo es: ",Per_B;
			Escribir "Calculando el area del triangulo....";
			Esperar 5 segundos;
			Area_B=(raiz(3)/4)*(b^2); //Formula Area Triangulo
			Escribir "El Area del Triangulo es: ",Area_B;
			Escribir "Calculando el volumen del Prisma Triangular....";
			Esperar 5 segundos;
			Vol=Area_B*h; //Formula Volumen Prisma Triangular
			Escribir "El volumen del Prisma Triangular es: ",Vol;
			Escribir "Calculando el area lateral del Prisma Triangular....";
			Esperar 5 segundos;
			Area_L=Per_B*h; //Formula Area Lateral Prisma Triangular
			Escribir "El area lateral del Prisma Triangular es: ",Area_L;
			Escribir "Calculando el area total del Prisma Triangular....";
			Esperar 5 segundos;
			Area_T=(2*Area_B)+Area_L; //Formula Area Prisma Triangular
			Escribir "El area total del Prisma Triangular es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 4: // Opcion Prisma Pentagonal
			Escribir "Ingresa la medida de la altura del prisma";
			Leer h;
			Escribir "Ingresa la medida del lado o arista";
			Leer l;
			Escribir "Ingresa el apotema del Pentagono";
			Leer ap;
			Escribir "Calculando el perimetro del Pentagono....";
			Esperar 5 segundos;
			Per_B=5*l; //Formula Perimetro Pentagono
			Escribir "El perimetro del Pentagono es: ",Per_B;
			Escribir "Calculando el area del Pentagono....";
			Esperar 5 segundos;
			Area_B=(Per_B*ap)/2; //Formula Area Pentagono
			Escribir "El Area del Pentagono es: ",Area_B;
			Escribir "Calculando el volumen del Prisma Pentagonal....";
			Esperar 5 segundos;
			Vol=Area_B*h; //Formula Volumen Prisma Pentagonal
			Escribir "El volumen del Prisma Pentagonal es: ",Vol;
			Escribir "Calculando el area lateral del Prisma Pentagonal....";
			Esperar 5 segundos;
			Area_L=Per_B*h; //Formula Area Lateral Prisma Pentagonal
			Escribir "El area lateral del Prisma Pentagonal es: ",Area_L;
			Escribir "Calculando el area total del Prisma Pentagonal....";
			Esperar 5 segundos;
			Area_T=(2*Area_B)+Area_L; //Formula Area Prisma Pentagonal
			Escribir "El area total del Prisma Pentagonal es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 5: // Opcion Cilindro
			Escribir "Ingresa el radio del circulo";
			Leer r;
			Escribir "Ingresa la medida de la altura del Cilindro";
			Leer h;
			Escribir "Calculando el volumen del Cilindro....";
			Esperar 5 segundos;
			Vol=PI*(r^2)*h; //Formula Volumen Cilindro
			Escribir "El volumen del Cilindro es: ",Vol;
			Escribir "Calculando el area del Cilindro....";
			Esperar 5 segundos;
			Area_T=(2*PI*r)*(r+h); //Formula Area Cilindro
			Escribir "El area total del Cilindro es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 6://Opcion Piramide Triangular
			Escribir "Ingresa la medida de la altura de la Piramide";
			Leer h;
			Escribir "Ingresa la medida del lado del Triangulo";
			Leer b;
			Escribir "Ingresa la apotema de la Piramide";
			Leer app;
			Escribir "Calculando el perimetro del Triangulo....";
			Esperar 5 segundos;
			Per_B=3*b; //Formula Perimetro Triangulo
			Escribir "El perimetro del Triangulo es: ",Per_B;
			Escribir "Calculando el area del Triangulo....";
			Esperar 5 segundos;
			Area_B=(raiz(3)/4)*(b^2); //Formula Area Triangulo
			Escribir "El Area del Triangulo es: ",Area_B;
			Escribir "Calculando el volumen de la Piramide Triangular....";
			Esperar 5 segundos;
			Vol=(Area_B*h)/3; //Formula Volumen Piramide Triangular
			Escribir "El volumen de la Piramide Triangular es: ",Vol;
			Escribir "Calculando el area lateral de la Piramide Triangular....";
			Esperar 5 segundos;
			Area_L=(Per_B*app)/2; //Formula Area Lateral Piramide Triangular
			Escribir "El area lateral de la Piramide Triangular es: ",Area_L;
			Escribir "Calculando el area total de la Piramide Triangular....";
			Esperar 5 segundos;
			Area_T=Area_B+Area_L; //Formula Area Piramide Triangular
			Escribir "El area lateral de la Piramide Triangular es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 7: //Opcion Piramide Cuadrangular
			Escribir "Ingresa la medida de la altura de la Piramide";
			Leer h;
			Escribir "Ingresa la medida del lado del Cuadrado";
			Leer l;
			Escribir "Ingresa la apotema de la Piramide";
			Leer app;
			Escribir "Calculando el perimetro del Cuadrado....";
			Esperar 5 segundos;
			Per_B=4*l; //Formula Perimetro Cuadrado
			Escribir "El perimetro del Cuadrado es: ",Per_B;
			Escribir "Calculando el area del Cuadrado....";
			Esperar 5 segundos;
			Area_B=l^2; //Formula Area Cuadrado
			Escribir "El Area del Cuadrado es: ",Area_B;
			Escribir "Calculando el volumen de la Piramide Cuadrangular....";
			Esperar 5 segundos;
			Vol=(Area_B*h)/3; //Formula Volumen Piramide Cuadrangular
			Escribir "El volumen de la Piramide Cuadrangular es: ",Vol;
			Escribir "Calculando el area lateral de la Piramide Cuadrangular....";
			Esperar 5 segundos;
			Area_L=(Per_B*app)/2; //Formula Area Lateral Piramide Cuadrangular
			Escribir "El area lateral de la Piramide Cuadrangular es: ",Area_L;
			Escribir "Calculando el area total de la Piramide Cuadrangular....";
			Esperar 5 segundos;
			Area_T=Area_B+Area_L; //Formula Area Piramide Cuadrangular
			Escribir "El area lateral de la Piramide Cuadrangular es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 8: //Opcion Piramide Rectangular
			Escribir "Ingresa la medida de la altura de la Piramide";
			Leer h;
			Escribir "Ingresa la medida de la base del Rectangulo";
			Leer b;
			Escribir "Ingresa la medida de la altura del Rectangulo";
			Leer l;
			Escribir "Ingresa la apotema de la Piramide";
			Leer app;
			Escribir "Calculando el perimetro del Rectangulo....";
			Esperar 5 segundos;
			Per_B=(2*b)+(2*l); //Formula Perimetro Rectangulo
			Escribir "El perimetro del Rectangulo es: ",Per_B;
			Escribir "Calculando el area del Rectangulo....";
			Esperar 5 segundos;
			Area_B=b*l; //Formula Area Rectangulo
			Escribir "El Area del Rectangulo es: ",Area_B;
			Escribir "Calculando el volumen de la Piramide Rectangular....";
			Esperar 5 segundos;
			Vol=(Area_B*h)/3; //Formula Volumen Piramide Rectangular
			Escribir "El volumen de la Piramide Rectangular es: ",Vol;
			Escribir "Calculando el area lateral de la Piramide Rectangular....";
			Esperar 5 segundos;
			Area_L=(Per_B*app)/2; //Formula Area Lateral Piramide Rectangular
			Escribir "El area lateral de la Piramide Rectangular es: ",Area_L;
			Escribir "Calculando el area total de la Piramide Rectangular....";
			Esperar 5 segundos;
			Area_T=Area_B+Area_L; //Formula Area Piramide Rectangular
			Escribir "El area lateral de la Piramide Rectangular es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 9://Opcion Piramide Pentagonal
			Escribir "Ingresa la medida de la altura de la Piramide";
			Leer h;
			Escribir "Ingresa la medida del lado o arista del Pentagono";
			Leer l;
			Escribir "Ingresa el apotema del Pentagono";
			Leer ap;
			Escribir "Ingresa la apotema de la Piramide";
			Leer app;
			Escribir "Calculando el perimetro del Pentagono....";
			Esperar 5 segundos;
			Per_B=5*l; //Formula Perimetro Pentagono
			Escribir "El perimetro del Pentagono es: ",Per_B;
			Escribir "Calculando el area del Pentagono....";
			Esperar 5 segundos;
			Area_B=(Per_B*ap)/2; //Formula Area Pentagono
			Escribir "El Area del Pentagono es: ",Area_B;
			Escribir "Calculando el volumen de la Piramide Pentagonal....";
			Esperar 5 segundos;
			Vol=(Area_B*h)/3; //Formula Volumen Piramide Pentagonal
			Escribir "El volumen de la Piramide Pentagonal es: ",Vol;
			Escribir "Calculando el area lateral de la Piramide Pentagonal....";
			Esperar 5 segundos;
			Area_L=(Per_B*app)/2; //Formula Area Lateral Piramide Pentagonal
			Escribir "El area lateral de la Piramide Pentagonal es: ",Area_L;
			Escribir "Calculando el area total de la Piramide Pentagonal....";
			Esperar 5 segundos;
			Area_T=Area_B+Area_L; //Formula Area Piramide Pentagonal
			Escribir "El area lateral de la Piramide Pentagonal es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		Caso 10://Opcion Piramide Hexagonal
			Escribir "Ingresa la medida de la altura de la Piramide";
			Leer h;
			Escribir "Ingresa la medida del lado o arista del Hexagono";
			Leer l;
			Escribir "Ingresa el apotema del Hexagono";
			Leer ap;
			Escribir "Ingresa la apotema de la Piramide";
			Leer app;
			Escribir "Calculando el perimetro del Hexagono....";
			Esperar 5 segundos;
			Per_B=6*l; //Formula Perimetro Hexagono
			Escribir "El perimetro del Hexagono es: ",Per_B;
			Escribir "Calculando el area del Hexagono....";
			Esperar 5 segundos;
			Area_B=(Per_B*ap)/2; //Formula Area Hexagono
			Escribir "El Area del Hexagono es: ",Area_B;
			Escribir "Calculando el volumen de la Piramide Hexagonal....";
			Esperar 5 segundos;
			Vol=(Area_B*h)/3; //Formula Volumen Piramide Hexagonal
			Escribir "El volumen de la Piramide Hexagonal es: ",Vol;
			Escribir "Calculando el area lateral de la Piramide Hexagonal....";
			Esperar 5 segundos;
			Area_L=(Per_B*app)/2; //Formula Area Lateral Piramide Hexagonal
			Escribir "El area lateral de la Piramide Hexagonal es: ",Area_L;
			Escribir "Calculando el area total de la Piramide Hexagonal....";
			Esperar 5 segundos;
			Area_T=Area_B+Area_L; //Formula Area Piramide Hexagonal
			Escribir "El area lateral de la Piramide Hexagonal es: ",Area_T;
			Escribir "Se realizo correctamente la operacion";
			
		De Otro Modo
			Escribir "Error de Procedimiento";
	FinSegun
FinProceso
	