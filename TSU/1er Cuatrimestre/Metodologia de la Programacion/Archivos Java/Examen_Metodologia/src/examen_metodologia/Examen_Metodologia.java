/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen_metodologia;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class Examen_Metodologia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner teclado = new Scanner(System.in); // Objeto teclado 
        int menu = 1;

        double numeros;
        int contador, resp, opcion; // LLevar el contenido de lo que van a mostrar , resp es para usarlo con sentencia rep (doWhile), opcion para escoger el tipo de samgre
        String registros[][] = new String[500][9]; // Creamos la tabla principal de los registros , 10 registros y los 9 encabezados 
        // Almacenar los datos dados por el profesor como es inicialmemte se va a llenar mediante codigo
        String inicioDate[][] = {
            {" 1", "María       ", "Martínez Sánches", "735852947        ", "30 ", "AB+           ", "correo1@gmail.com", "    DSM", "9.8"},
            {" 2", "Luis Eduardo", "Gonzalez Flores ", "7771239543", "24  ", "A+            ", "correo2@gmail.com", "    IRD", "9"},
            {" 3", "Camila  ", "Cárdenas Talamantes ", "7729517664", "15  ", "O-            ", "correo3@gmail.com", "    IRD", "7"},
            {" 4", "Noah     ", "Talamantes Gutiérrez", "735964660 ", "22  ", "B-            ", "correo4@gmail.com", "    DSM", "8.6"},
            {" 5", "Gabriela  ", "Torcazas Díaz       ", "7774681980", "28  ", "AB-           ", "correo5@gmail.com", "    DSM", "6.9"}};

        contador = 4; //LLevar la cuenta de registros Cambiar si va hacer mas registros precargados 
        //Se va utilizar un vector llamados titulos almacenado los encabezados de la tabla 
        String[] titulos = {"ID", "Nombre(s)", "Apellidos", "Telefono", "Edad", "Tipo de sangre", "Email", "Carrera", "Promedio"};
        for (int i = 0; i < inicioDate.length; i++) { //Ingreamos filas
            for (int j = 0; j < inicioDate[i].length; j++) { //Ingresamos columnas 
                registros[i][j] = inicioDate[i][j]; //Guardamos en el vector principal
            }
        }
        do {
            System.err.println("--------Tabla Inicial---------");
            //Mostramos los encabezados
            for (int k = 0; k < titulos.length; k++) {
                System.out.print(titulos[k] + " \t ");
            }
            System.out.println("");

            // Mostramos la carga precargada
            for (int i = 0; i < contador + 1; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(registros[i][j] + "\t");

                }
                System.out.println("");
            }

            // Tabla llamada tipo sangre 
            String[][] sangre = {{"1", "A+"}, {"2", "B+"}, {"3", "AB+"}, {"4", "O+"}, {"5", "A-"}, {"6", "B-"}, {"7", "AB-"}, {"8", "0-"}};

            System.out.println("-------------------Menu-----------------------");
            System.out.println("1. Añadir registro, 2.Modificar campo, 3.Mostrar promedios, 4,Alumonos Reprobados, 5.Ordenar"); //Mostramos las opciones que va a realizar
            menu = teclado.nextInt();
            if (menu <= 5) { //Validamos la opcion del menu
                if (menu == 1) { //Nuevo registro 
                    do {
                        System.out.println("---------Ingresa un nuevo registro----------");
                        contador++; // ----------------------------------------------------------Id Nuevo automatico 
                        for (int i = 1; i < titulos.length; i++) {
                            registros[contador][0] = " " + (contador + 1); // Ingresamos ID                

                            // Ingresamos tabla para pedir que tipo de samgre
                            if (i != 5) {
                                System.out.println("Ingresa " + titulos[i] + ": ");
                                String medio = teclado.next();
                                medio += teclado.nextLine();
                                registros[contador][i] = medio; //Ingresamos datos del registro  
                            } else {
                                //Mostramos la tabla de tipos de sangre
                                System.out.println("Escoge el Tipos de Sangre");
                                for (int l = 0; l < sangre.length; l++) {
                                    for (int j = 0; j < sangre[l].length; j++) {
                                        System.out.print("->" + sangre[l][j]); //Mostramos las opciones
                                    }
                                    System.out.println("");
                                }

                                boolean pasa = false; //Para dar paso al siguiente informacio
                                do {
                                    System.out.print("Ingresa selección: ");
                                    opcion = teclado.nextInt(); //Ingresa el numero de fila donde se encuentra el valor de tipo de sangre
                                    if (opcion < 9) { //Validamos las opciones del menu de sangre
                                        pasa = true;
                                    } else {
                                        System.err.println("Dato incorrecto , intenta nuevamente ");
                                    }
                                } while (!pasa);//Si no es una dato correcto se vuelve a intentar hasta que lo sea

                                registros[contador][i] = sangre[opcion - 1][1]; //Ingresamos el dato escogido 
                            }
                        }

                        System.out.println("¿Deseas continuar agregando registros? 1.Si 2.No"); ///Para seguir agregando registros 
                        resp = teclado.nextInt();
                    } while (resp == 1);
                }
                if (menu == 2) { //Modificar campos 
                    do {
                        System.out.println(" ------------------MODIFICAR-----------");
                        int columna = 0, id = 0; // Variables para localizar el dato       
                        boolean idbol = false, columbol = false;
                        do {
                            System.out.println("Introduce el ID a buscar ");
                            id = teclado.nextInt(); // Introducimos el id menos 1 ya que empezamos desde 0
                            if (id <= contador + 1) { //Verificamos que exista ese ID
                                for (int k = 1; k < titulos.length; k++) {
                                    String mostar = titulos[k] + "-->" + registros[id - 1][k].replaceAll("\\s+", "");
                                    //El repalce es para quitar espacios , aquí mostramos los datos de la persona con el id
                                    //Para mejor agilidad solo debe escoger un numero de lo que quiere cambiar
                                    System.out.println(k + "." + mostar);
                                    idbol = true;//Validamso que busco bien
                                }
                                System.out.println("Ingrese el numero de la información que desea cambiar: ");// Requerimso que dato vamos a cambiar
                                columna = teclado.nextInt();
                                if (columna < 9) { //Numero de columnas
                                    //Mostramos dato a cambiar
                                    System.out.println("Dato a querer remplazar --> " + registros[id - 1][columna]);
                                    System.out.println("Ingrese dato nuevo: ");
                                    String cambio = teclado.next();
                                    cambio += teclado.nextLine();
                                    registros[id - 1][columna] = cambio; //Hace el cambio de información
                                    System.out.println("\n-------->Dato remplazado correctamente");
                                    columbol = true; //Validamso que busco bien
                                } else {
                                    System.out.println("------Dato incorrecto------");
                                }
                            } else {
                                System.out.println("----Dato incorrecto-------");
                            }
                        } while (!idbol || !columbol); //Si unos de los dos se mantienen en falso , se repite hasta que ingrese los datos bien
                        System.out.println("¿Deseas continuar modificando datos? 1.si 2.no");// Para seguir cambaindo datps
                        resp = teclado.nextInt();
                    } while (resp == 1);
                }
                if (menu == 3) {
                    // promedio más bajo, el alumno con el promedio más alto y mostrar el promedio de todo 
                    double mayor = 0, menor = 0, result = 0; // variables a usar
                    String empate = ""; // Donde se guardaran los id 
                    for (int i = 0; i < contador + 1; i++) { // Pasamos por la columna de los promedios
                        numeros = Double.parseDouble(registros[i][8]); //de cadena a doubles ea decir numeros con decimal

                        if (numeros == mayor) { // Si hay repetidos guardamos el id de cada estudiante 
                            mayor = numeros;
                            String numeroID = registros[i][0];
                            empate += "," + numeroID;
                        } else {
                            if (numeros > mayor) {
                                mayor = numeros;//Busacmos el menor
                                empate = registros[i][0];
                            }
                        }
                    }
                    System.out.println("Id del estudiante(s) con el promedio mas alto: " + empate);
                    System.out.println("Promedio " + mayor);

                    menor = mayor;
                    for (int i = 0; i < contador + 1; i++) { // Pasamos por la columna de los promedios
                        numeros = Double.parseDouble(registros[i][8]); //de cadena a doubles ea decir numeros con decimal
                        result += numeros;//Suamos toda la columna en donde estan los promedios
                        if (menor == numeros) { // Si hay repetidos guardamos el id de cada estudiante 
                            menor = numeros;
                            String numeroID = registros[i][0]; //Guardamos los id en una cadena de caracteres
                            empate += "," + numeroID;
                        } else {
                            if (numeros < menor) {
                                menor = numeros;
                                empate = registros[i][0];//Guardamos el id
                            }
                        }
                    }
                    System.out.println("Id del estudiante(s) con el promedio más bajo: " + empate);
                    System.out.println("Promedio " + menor);
                    System.out.println("El promedio en general es:" + result / (contador + 1));//Promdeio general 
                }
                if (menu == 4) {
                    //Mostarr los de promedio menor o igaul a 7
                    int[] reprobados = new int[500];
                    int cont = 0;
                    for (int i = 0; i < contador + 1; i++) { // Pasamos por la columna de los promedios
                        numeros = Double.parseDouble(registros[i][8]); //de cadena a doubles ea decir numeros con decimal
                        if (numeros <= 7) { // Si son menores                  
                            reprobados[cont] = i;
                            cont++;//Contar cuantos alumnos hay reprobados
                        }
                    }
                    System.out.println("--------------Alumnos reprobados------------------------------------------------------------------");
                    for (int i = 0; i < cont; i++) { //Mostar los alumnos 
                        // System.out.println(reprobados[i]+1);
                        int fila = reprobados[i]; //Sabemos que fila necesitamos ya que guardamos el idnice cada que habia un reprobado

                        for (int j = 0; j < 9; j++) {
                            System.out.print(registros[fila][j] + "  | "); //Mostramos las filas de los reprobados
                        }
                        System.out.println("");
                    }
                    System.out.println("--------------------------------------------------------------------------------------------");
                }
                if (menu == 5) {
                    //Ordenamientoooooo0000000000000
                    int criterio = 0, orden = 0;
                    System.out.println("------------------------Ordenamiento------------------");
                    System.out.println("Escoge sobre la columna que quieres ordenas (criterio) ");
                    for (int k = 0; k < titulos.length; k++) {
                        System.out.println((k + 1) + "." + titulos[k].replaceAll("\\s+", "")); //Mostramos el encabezado     
                    }

                    System.out.println("Ingrese el numero:");
                    criterio = teclado.nextInt();//Escogemos en la comluna a bvasarse                  

                    System.out.println("\n1. Ascendente (Menor a Mayor), 2.Descendente (Mayor-Menor)");
                    orden = teclado.nextInt(); //Selecionamos la manera de ordenar

                    double promedio[] = new double[500]; // Aqui voy a guardar los promedios           
                    String aux[]; // Me va ayudar hacer los cambios del registro 
                    double A;// incognicataaaaaa
                    System.out.println("Se acomodo por: " + titulos[criterio - 1]);

                    if (criterio == 1 || criterio == 4 || criterio == 5 || criterio == 9) { //Aqui va entrar cuando compara numeros 

                        //Aqui voyb a psar los promedios de texto a int 
                        for (int i = 0; i < contador + 1; i++) { // filas 
                            double numerosparse = Double.parseDouble(registros[i][criterio - 1]);
                            promedio[i] = numerosparse;
                        }

                        if (orden == 1) {
                            for (int i = 0; i < contador; i++) {    //es le numero de filas que hay                        
                                for (int j = 0; j < contador - i; j++) {

                                    if (promedio[j + 1] < promedio[j]) {
                                        aux = registros[j + 1];
                                        registros[j + 1] = registros[j]; //Si intercambian los dtos 
                                        registros[j] = aux;

                                        A = promedio[j + 1];
                                        promedio[j + 1] = promedio[j];
                                        promedio[j] = A;
                                    }

                                }
                            }
                        } else {// Aqui se van a comparar cadenas 
                            for (int i = 0; i < contador; i++) {    //  es le numero de filas que hay                        
                                for (int j = 0; j < contador - i; j++) {

                                    if (promedio[j + 1] > promedio[j]) {
                                        aux = registros[j + 1];
                                        registros[j + 1] = registros[j]; //Se intercambain los datos 
                                        registros[j] = aux;

                                        A = promedio[j + 1];
                                        promedio[j + 1] = promedio[j];
                                        promedio[j] = A;
                                    }

                                }
                            }
                        }

                    } else { //Aqui va entrar cnado comparar cadenas 

                        if (orden == 1) {///Menos a mayor A-z
                            for (int i = 0; i < contador; i++) {
                                for (int j = 0; j < contador - i; j++) {
                                    if (registros[j][criterio - 1].compareToIgnoreCase(registros[j + 1][criterio - 1]) > 0) {
                                        aux = registros[j + 1];
                                        registros[j + 1] = registros[j];
                                        registros[j] = aux;
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < contador; i++) {
                                for (int j = 0; j < contador - i; j++) {
                                    if (registros[j][criterio - 1].compareToIgnoreCase(registros[j + 1][criterio - 1]) < 0) {
                                        aux = registros[j + 1];
                                        registros[j + 1] = registros[j];
                                        registros[j] = aux;
                                    }
                                }
                            }
                        }

                    }

                }

                if (menu != 3 && menu != 4) { //Mostramos como queda al final la tabla para notar cambios hechos
                    System.out.println("---------------------------Tabla modfificada-------------------------------------");
                    //Mostramos los encabezados
                    System.out.println("");
                    for (int k = 0; k < titulos.length; k++) {
                        System.out.print(titulos[k] + " \t ");
                    }
                    System.out.println("");

                    // Mostramos la carga precargada
                    for (int i = 0; i < contador + 1; i++) {
                        for (int j = 0; j < 9; j++) {
                            System.out.print(registros[i][j] + "\t");

                        }
                        System.out.println("");
                    }
                }

            } else {
                System.err.println("Intente otro dato");
            }
            System.out.println("\nDeseas Realizar otra acción del menu 1.Si , 2.No"); ///Para seguir en el programa
            resp = teclado.nextInt();
        } while (resp == 1);
    }

}
