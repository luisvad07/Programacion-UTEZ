/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu2;

import java.util.Scanner;

/**
 *
 * @author CC10
 */
public class TestExamenU2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Metodo para leer desde teclado
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");

        //Llamada de la clase ContraAccess
        ContraAccess accesso = new ContraAccess();

        //Llama la clase Arreglos
        Arreglos matriz;

        //Variables para validar la contraseÃ±a
        String tecladoUsuario;
        boolean cle = false;

        //Variables para la longitud del arreglo
        int longitud = 0, indice = 0;

        //variables para leer datos
        String nombre, matricula;
        int carrera;

        //Variables para relizar cambios de carrera
        int opcion = 0, opcCarrera, auxt = -1, aux = -1, CostoCarrera = 0;
        String leerMatricula;
        int nuevaCarrera;

        //Variables para cambiar costo de carrera y pagar carrera
        double carrera1 = 3500.0, carrera2 = 4200.0, carrera3 = 3800.0, nCarrera1 = 0, nCarrera2 = 0, nCarrera3 = 0;
        double promedioFinal, totalPagar = 0, acumPago = 0, newTotal = 0;

        //Variables para leer datos para generar matricula
        String name, day, month, alet;

        //Variables para imprimir descuentos
        double valorDescuento1, valorDescuento2, valorDescuento3, valorDescuento50;
        double DESC1 = 0.15, DESC2 = 0.1, DESC3 = 0.05, DESC50 = 0.5;

        System.out.println("--------- Bienvenido al sistema de inscripciones ---------");
        for (int i = 0; i < 3 && !cle; i++) {

            System.out.println("Ingresa la contraseña (Tienes 3 intentos)");
            System.out.print("Intento " + (i + 1) + ": ");
            tecladoUsuario = teclado.next();

            cle = accesso.verificar(tecladoUsuario);

            if (cle == true) {
                System.out.println("CONTRASEÑA CORRECTA");
                System.out.println("\n");
                System.out.print("Ingresa la cantidad de alumnos a inscribir: ");
                longitud = teclado.nextInt();
                matriz = new Arreglos(longitud);

                System.out.println("========================================================");
                System.out.println("               BIENVENIDO");
                System.out.println("========================================================");
                do {
                    System.out.println("MENU");
                    System.out.println("1. Inscribir alumno");
                    System.out.println("2. Cambio de la carrera del alumno");
                    System.out.println("3. Cambio del costo de la carrera");
                    System.out.println("4. Salir");

                    System.out.print("Seleccione la opcion a realizar: ");
                    opcion = teclado.nextInt();

                    switch (opcion) {

                        case 1: //Opcion Inscribir a un alumno

                            if (matriz.cupos()) {

                                System.out.println("\n");
                                System.out.println("==== GENERANDO TU MATRICULA ====");
                                System.out.print("Ingresa las primeras dos letras de tu nombre (EN MAYUSCULAS): ");
                                name = teclado.next();
                                System.out.print("Ingresa tu dia de nacimiento (2 numeros, si el dia es de un numero, coloca un 0 antes): ");
                                day = teclado.next();
                                System.out.print("Ingresa tu mes de nacimiento (2 numeros, si el mes es de un numero, coloca un 0 antes): ");
                                month = teclado.next();
                                do {
                                    System.out.print("Ingresa 2 numeros aleatorios: ");
                                    alet = teclado.next();
                                    matricula = name.substring(0, 2).concat(day).concat(month).concat(alet);
                                    auxt = matriz.busquedaMatt(matricula);
                                    if (auxt == -1) {
                                        matriz.setMatrizMatricula(matricula);
                                        System.out.println("Matricula Generada exitosamente");
                                        System.out.println("Tu matricula es: " + matriz.getMatricula(indice));
                                        System.out.println("\n");
                                    } else {
                                        System.out.println("Ya existe esa matricula!");
                                    }
                                } while (auxt != -1 && auxt != -2);
                                System.out.print("Ingresa tu nombre: ");
                                nombre = teclado.next();
                                matriz.setMatrizNombre(nombre);
                                System.out.println("\n");

                                do {
                                    System.out.println("\n");
                                    System.out.println("Carrera------>Costo");
                                    System.out.println("1------------>$" + carrera1);
                                    System.out.println("2------------>$" + carrera2);
                                    System.out.println("3------------>$" + carrera3);
                                    System.out.print("Seleccione la carrera: ");
                                    carrera = teclado.nextInt();
                                    matriz.setMatrizCarrera(carrera);
                                    opcCarrera = carrera;
                                } while (carrera > 3 || carrera < 1);

                                System.out.println("\n");
                                System.out.print("Promedio final de la preparatoria: ");
                                promedioFinal = teclado.nextDouble();

                                switch (opcCarrera) {
                                    case 1:
                                        if (promedioFinal <= 10 && promedioFinal >= 9.5) {
                                            System.out.println("Usted tiene un descuento del 15%");
                                            valorDescuento1 = carrera1 * DESC1;
                                            totalPagar = carrera1 - valorDescuento1;
                                        } else if (promedioFinal < 9.5 && promedioFinal >= 9) {
                                            System.out.println("Usted tiene un descuento del 10%");
                                            valorDescuento2 = carrera2 * DESC2;
                                            totalPagar = carrera2 - valorDescuento2;
                                        } else if (promedioFinal <= 9 && promedioFinal >= 8.5) {
                                            System.out.println("Usted tiene un descuento del 5%");
                                            valorDescuento3 = carrera3 * DESC3;
                                            totalPagar = carrera3 - valorDescuento3;
                                        } else if (promedioFinal < 8.5) {
                                            System.out.println("Usted no tiene descuento");
                                            totalPagar = carrera1;
                                        }
                                        break;
                                    case 2:
                                        if (promedioFinal <= 10 && promedioFinal >= 9.5) {
                                            System.out.println("Usted tiene un descuento del 15%");
                                            valorDescuento1 = carrera1 * DESC1;
                                            totalPagar = carrera1 - valorDescuento1;
                                        } else if (promedioFinal < 9.5 && promedioFinal >= 9) {
                                            System.out.println("Usted tiene un descuento del 10%");
                                            valorDescuento2 = carrera2 * DESC2;
                                            totalPagar = carrera2 - valorDescuento2;
                                        } else if (promedioFinal <= 9 && promedioFinal >= 8.5) {
                                            System.out.println("Usted tiene un descuento del 5%");
                                            valorDescuento3 = carrera3 * DESC3;
                                            totalPagar = carrera3 - valorDescuento3;
                                        } else if (promedioFinal < 8.5) {
                                            System.out.println("Usted no tiene descuento");
                                            totalPagar = carrera2;
                                        }
                                        break;
                                    case 3:
                                        if (promedioFinal <= 10 && promedioFinal >= 9.5) {
                                            System.out.println("Usted tiene un descuento del 15%");
                                            valorDescuento1 = carrera1 * DESC1;
                                            totalPagar = carrera1 - valorDescuento1;
                                        } else if (promedioFinal < 9.5 && promedioFinal >= 9) {
                                            System.out.println("Usted tiene un descuento del 10%");
                                            valorDescuento2 = carrera2 * DESC2;
                                            totalPagar = carrera2 - valorDescuento2;
                                        } else if (promedioFinal <= 9 && promedioFinal >= 8.5) {
                                            System.out.println("Usted tiene un descuento del 5%");
                                            valorDescuento3 = carrera3 * DESC3;
                                            totalPagar = carrera3 - valorDescuento3;
                                        } else if (promedioFinal < 8.5) {
                                            System.out.println("Usted no tiene descuento");
                                            totalPagar = carrera3;
                                        }
                                        break;
                                }
                                System.out.println("Total a pagar: " + totalPagar);
                                acumPago += totalPagar;

                            } else {
                                System.out.println("Ya no hay cupos");
                            }
                            break;

                        case 2: //Opcion Cambio de carrera a un alumno

                            System.out.print("Ingresa la matricula del alumno: ");
                            leerMatricula = teclado.next();
                            aux = matriz.busqueda(leerMatricula);
                            if (aux != -1) {
                                do {
                                    System.out.println("Carrera------>Costo");
                                    System.out.println("1------------>$" + carrera1);
                                    System.out.println("2------------>$" + carrera2);
                                    System.out.println("3------------>$" + carrera3);
                                    System.out.print("Ingresa la nueva carrera para el alumno con matricula " + leerMatricula + ": ");
                                    nuevaCarrera = teclado.nextInt();
                                    if (nuevaCarrera > 3 || nuevaCarrera < 1) {
                                        System.out.println("ERROR... tipo de membresia incorrecta");
                                    } else {
                                        if (confirmarCambio() == 1) {
                                            carrera = nuevaCarrera;
                                            matriz.setMatrizCarrera(carrera);
                                            System.out.println("Cambio exitoso");
                                            System.out.println("La nueva carrera para el alumno con matricula " + leerMatricula + " es " + matriz.getCarrera(indice, carrera));
                                            switch (carrera) {
                                                case 1:
                                                    newTotal = carrera1;
                                                    break;
                                                case 2:
                                                    newTotal = carrera2;
                                                    break;
                                                case 3:
                                                    newTotal = carrera3;
                                                    break;
                                            }
                                            if (newTotal < totalPagar) {
                                                System.out.println("Se tiene que pagar diferencia");
                                                double valor = newTotal - totalPagar;
                                                System.out.println("Diferencia a pagar: $" + valor);
                                                acumPago += valor;
                                            } else {
                                                System.out.println("Reembolso del 50% en tu pago");
                                                valorDescuento50 = newTotal * DESC50;
                                                System.out.println("Se te regresa " + valorDescuento50);
                                                acumPago -= valorDescuento50;
                                            }
                                        } else {
                                            System.out.println("ERROR... cambio no realizado");
                                        }
                                    }
                                } while (nuevaCarrera > 4 || nuevaCarrera < 1);

                            } else {
                                System.out.println("ERROR... matricula no encontrada");
                            }
                            break;

                        case 3: //Opcion Cambio de costo de la carrera

                            System.out.println("Carrera------>Costo");
                            System.out.println("1------------>$" + carrera1);
                            System.out.println("2------------>$" + carrera2);
                            System.out.println("3------------>$" + carrera3);
                            System.out.println("¿Que carrera quieres cambiarle el costo?");
                            CostoCarrera = teclado.nextInt();

                            switch (CostoCarrera) {
                                case 1:
                                    System.out.print("Ingresa el nuevo costo de la carrera 1: ");
                                    nCarrera1 = teclado.nextDouble();
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
                                    nCarrera2 = teclado.nextDouble();
                                    if (confirmarCambio() == 1) {
                                        carrera2 = nCarrera2;
                                        System.out.println("Cambio exitoso");
                                        System.out.println("El costo de la carrera 2 ha cambiado a $" + carrera2);
                                    } else {
                                        System.out.println("ERROR... cambios no realizados");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Ingresa el nuevo costo de la carrera 3: ");
                                    nCarrera3 = teclado.nextDouble();
                                    if (confirmarCambio() == 1) {
                                        carrera3 = nCarrera3;
                                        System.out.println("Cambio exitoso");
                                        System.out.println("El costo de la carrera 3 ha cambiado a $" + carrera3);
                                    } else {
                                        System.out.println("ERROR... cambios no realizados");
                                    }
                                    break;
                            }
                            break;

                        case 4: //Opcion Salir

                            System.out.println("Total a pagar el dia de hoy:" + acumPago);
                            break;

                        default:

                            System.out.println("La opcion que selecciono es erronea");
                            break;

                    }
                } while (opcion != 4);
            }
        }
        if (cle == false) {
            System.out.println("\n");
            System.out.println("Has superado el limite de intentos. No puedes ingresar al sistema!");
        }
    }

    public static int confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int respuesta;
        Scanner scar = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        respuesta = scar.nextInt();
        scar.skip("\n");
        return respuesta; //Si es Verdadero acepta el cambio. Si es Falso, lo rechaza
    }

}
