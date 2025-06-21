/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio.burbuja;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class EjercicioBurbuja {

    /**
     * @param args the command line arguments
     */
        
        public static int confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int respuesta;
        Scanner scar = new Scanner(System.in);
        System.out.println("Â¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        respuesta = scar.nextInt();
        scar.skip("\n");
        return respuesta; //Si es Verdadero acepta el cambio //Si es Falso, lo rechaza
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner leer = new Scanner(System.in);
        leer.useDelimiter("\n");

        int opcion = 0, contadorAlumnos = 0, opcCarerra, aux = -1, cambiarCostoCarrera = 0;
        int carrera;
        String leerMatricula, nuevoNombre;
        double promedioFinal, totalPagar = 0, acumPago = 0, carrera1 = 1500, carrera2 = 2000, nCarrera1 = 0, nCarrera2 = 0;

        String[] matrizMatricula = new String[100];
        String[] matrizNombre = new String[100];
        int[] matrizCarrera = new int[100];
        //int numCarrera[] = {1,2};
        // int carreraCosto[] = {1500, 2000};

        do {
            System.out.println("MENU");
            System.out.println("1. Inscribir alumno");
            System.out.println("2. Cambio del nombre");
            System.out.println("3. Cambio del costo de la carrera");
            System.out.println("4. Salir");

            System.out.print("Seleccione la opcion a realizar: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    contadorAlumnos++;
                    if (contadorAlumnos <= 100) {

                        System.out.print("Matricula: ");
                        matrizMatricula[contadorAlumnos - 1] = leer.next();

                        System.out.print("Nombre: ");
                        matrizNombre[contadorAlumnos - 1] = leer.next();

                        System.out.println("Carrera------>Costo");
                        System.out.println("1------------>$" + carrera1);
                        System.out.println("2------------>$" + carrera2);

                        System.out.print("Seleccione la carrera: ");
                        matrizCarrera[contadorAlumnos - 1] = leer.nextInt();
                        opcCarerra = matrizCarrera[contadorAlumnos - 1];

                        System.out.print("Promedio final de la preparatoria: ");
                        promedioFinal = leer.nextDouble();

                        if (opcCarerra == 1) {
                            if (promedioFinal <= 10 && promedioFinal >= 9.5) {
                                System.out.println("Usted tiene un descuento del 15%");
                                totalPagar = carrera1 * 1.15;
                            } else if (promedioFinal < 9.5 && promedioFinal >= 9) {
                                System.out.println("Usted tiene un descuento del 10%");
                                totalPagar = carrera1 * 1.10;
                            } else if (promedioFinal <= 9 && promedioFinal >= 8.5) {
                                System.out.println("Usted tiene un descuento del 5%");
                                totalPagar = carrera1 * 1.5;
                            }
                        } else if (opcCarerra == 2) {
                            if (promedioFinal <= 10 && promedioFinal >= 9.5) {
                                System.out.println("Usted tiene un descuento del 15%");
                                totalPagar = carrera2 * 1.15;
                            } else if (promedioFinal < 9.5 && promedioFinal >= 9) {
                                System.out.println("Usted tiene un descuento del 10%");
                                totalPagar = carrera2 * 1.10;
                            } else if (promedioFinal <= 9 && promedioFinal >= 8.5) {
                                System.out.println("Usted tiene un descuento del 5%");
                                totalPagar = carrera2 * 1.5;
                            }
                        }
                        System.out.println("Total a pagar: " + totalPagar);
                        acumPago += totalPagar;

                    } else {
                        System.out.println("ERROR... cupos agotados");
                    }
                    break;
                case 2:

                    System.out.print("Ingresa la matricula del alumno: ");
                    leerMatricula = leer.next();
                    for (int i = 0; i < matrizMatricula.length; i++) {
                        if (matrizMatricula[i].equals(leerMatricula)) {
                            aux = i;
                            break;
                        }
                    }
                    if (aux != -1) {
                        System.out.println("Ingresa el nuevo nombre para " + matrizNombre[aux]
                                + " con matricula " + leerMatricula);
                        nuevoNombre = leer.next();
                        if (confirmarCambio() == 1) {
                            matrizNombre[aux] = nuevoNombre;
                            System.out.println("Cambio exitoso");
                            System.out.println("El nuevo nombre para el alumno con matricula " + leerMatricula + " es " + matrizNombre[aux]);
                        } else {
                            System.out.println("ERROR... cambio no realizado");
                        }

                    } else {
                        System.out.println("ERROR... matricula no encontrada");
                    }
                    break;
                case 3:
                    System.out.println("Carrera------>Costo");
                    System.out.println("1------------>$" + carrera1);
                    System.out.println("2------------>$" + carrera2);
                    System.out.println("¿Que carrera quieres cambiar?");
                    cambiarCostoCarrera = leer.nextInt();

                    switch (cambiarCostoCarrera) {
                        case 1:
                            System.out.print("Ingresa el nuevo costo de la carrera 1: ");
                            nCarrera1 = leer.nextDouble();
                            if (confirmarCambio() == 1) {
                                carrera1 = nCarrera1;
                                System.out.println("Cambio exitoso");
                                System.out.println("El costo de la carrera 1 ha cambiado a $" + carrera1);
                            } else {
                                System.out.println("ERROR... cambios no realizados");
                            }
                            break;
                        case 2:
                            System.out.print("Ingresa el nuevo costo de la carrera 2: ");
                            nCarrera2 = leer.nextDouble();
                            if (confirmarCambio() == 1) {
                                carrera2 = nCarrera2;
                                System.out.println("Cambio exitoso");
                                System.out.println("El costo de la carrera 1 ha cambiado a $" + carrera2);
                            } else {
                                System.out.println("ERROR... cambios no realizados");
                            }
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Total a pagar el dia de hoy:" + acumPago);
                    break;
                default:
                    break;
            }
        } while (opcion != 4);
    
}

}