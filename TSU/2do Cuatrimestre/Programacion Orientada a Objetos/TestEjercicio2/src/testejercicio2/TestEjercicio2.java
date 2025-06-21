/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testejercicio2;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestEjercicio2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Metodo para leer desde teclado
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");

        //Llama la clase Arreglos
        Arreglos matriz;

        //Variables para la longitud del arreglo
        int longitud = 0, ap = -1, p = 0;

        //variables para leer datos
        String nombre, clave = null;
        int edad;
        double estatura;

        //Variables para realizar busquedas
        int opcion = 0, auxt = -1;

        //Variables para realizar bajas
        String leerClave;
        boolean baja;

        //Variables para realizar cambios
        boolean existe;
        char choix;
        boolean change;

        System.out.println("--------- Bienvenido al sistema de inscripciones ---------");
        System.out.print("Ingresa la cantidad de personas a inscribir: ");
        longitud = teclado.nextInt();
        matriz = new Arreglos(longitud);

        System.out.println("========================================================");
        System.out.println("               BIENVENIDO");
        System.out.println("========================================================");
        do {
            System.out.println("MENU");
            System.out.println("1.- Alta de una Persona");
            System.out.println("2.- Baja de una Persona");
            System.out.println("3.- Cambio de Datos");
            System.out.println("4.- Consulta de una persona");
            System.out.println("5.- Consulta todas las personas");
            System.out.println("6.- Salir");

            System.out.print("Seleccione la opcion a realizar: ");
            opcion = teclado.nextInt(); 
            switch (opcion) {
                case 1: // Alta de una persona
                    if (ap != longitud) {
                        System.out.println("\n");
                        do {
                            System.out.print("Ingresa tu clave: ");
                            clave = teclado.next();
                            auxt = matriz.busqueda(clave);
                            if (auxt == -1) {
                                ap++;
                                matriz.setMatrizClave(ap, clave);
                                System.out.println("Clave " + clave + " guardada exitosamente en la posicion " + ap);
                                System.out.print("Ingresa tu nombre: ");
                                nombre = teclado.next();
                                matriz.setMatrizNombre(ap, nombre);
                                System.out.print("Ingresa tu edad: ");
                                edad = teclado.nextInt();
                                matriz.setMatrizEdad(ap, edad);
                                System.out.print("Ingresa tu estatura: ");
                                estatura = teclado.nextDouble();
                                matriz.setMatrizEstatura(ap, estatura);
                            } else {
                                System.out.println("Ya existe esa matricula, Favor de Poner otra clave!");
                            }
                        } while (auxt != -1 && auxt != -2);
                    } else {
                        System.out.println("Ya no se pueden registrar mas personas!");
                    }
                    System.out.println("\n");
                    break;
                case 2: // Baja de una persona
                    if (ap != -1) {
                        System.out.println("\n");
                        System.out.println("Se dara de baja a la persona: " + matriz.getMatrizClave(ap));
                        baja = confirmarBaja();
                        if (baja) {
                            matriz.eliminar(clave);
                            ap--;
                        } else {
                            System.out.println("Error al realizar la baja!");
                        }
                    } else {
                        System.out.println("No hay Registros");
                    }
                    System.out.println("\n");
                    break;
                case 3: // Cambio de Datos
                    if (ap != -1) {
                        System.out.println("\n");
                        System.out.print("Ingresa la clave de la persona: ");
                        leerClave = teclado.next();
                        for (int j = 0; j <= ap; j++) {
                            if (leerClave.equals(matriz.getMatrizClave(j))) {
                                System.out.println("Persona # " + j);
                                System.out.println("Clave: " + matriz.getMatrizClave(j));
                                System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                                System.out.println("Edad: " + matriz.getMatrizEdad(j));
                                System.out.println("Estatura: " + matriz.getMatrizEstatura(j));
                            }//if
                        }//for
                        System.out.println("\n");
                        System.out.println("¿Que informacion deseas cambiar?");
                        System.out.println("a.- Cambiar nombre\n"
                                + "b.- Cambiar edad\n"
                                + "c.- Cambiar estatura\n"
                                + "d.- Regresar");
                        System.out.print("Ingresa la letra seleccionada: ");
                        choix = teclado.next().charAt(0);
                        switch (choix) {
                            case 'a': //Modificar Nombre
                                System.out.print("Ingresa tu nuevo nombre: ");
                                nombre = teclado.next();
                                change = confirmarCambio();
                                if (change) {
                                    matriz.setMatrizNombre(nombre);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 'b': // Modificar Edad
                                System.out.print("Ingresa tu nueva edad: ");
                                edad = teclado.nextInt();
                                change = confirmarCambio();
                                if (change) {
                                    matriz.setMatrizEdad(edad);
                                    System.out.print("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 'c': //Modificar Estatura
                                System.out.print("Ingresa tu nueva estatura: ");
                                estatura = teclado.nextDouble();
                                change = confirmarCambio();
                                if (change) {
                                    matriz.setMatrizEstatura(estatura);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 'd': //Regresar al menu principal
                                System.out.println("Regresando a menu principal...");
                                break;
                            default:
                                System.out.println("Error de Opcion del submenu!");
                                break;
                        }
                    } else {
                        System.out.println("No Hay Datos");
                    }
                    System.out.println("\n");
                    break;
                case 4: //Consulta de una persona
                    if (ap != -1) {
                        existe = false;
                        System.out.println("\n");
                        System.out.print("Ingresa la clave de la persona: ");
                        leerClave = teclado.next();

                        for (int j = 0; j <= ap; j++) {
                            if (leerClave.equals(matriz.getMatrizClave(j))) {
                                existe = true;
                                System.out.println("\n");
                                System.out.println("Persona # " + j);
                                System.out.println("Clave: " + matriz.getMatrizClave(j));
                                System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                                System.out.println("Edad: " + matriz.getMatrizEdad(j));
                                System.out.println("Estatura: " + matriz.getMatrizEstatura(j));
                                System.out.println("\n");
                            }//if
                        }//for
                        if (existe == false) {
                            System.out.println("La Clave no Existe");
                        }
                    } else {
                        System.out.println("No hay Datos");
                    }
                    break;
                case 5: // Consulta todas las personas
                    for (int j = 0; j < matriz.longitud(); j++) {
                        System.out.println("\n");
                        System.out.println("Persona # " + j);
                        System.out.println("Clave: " + matriz.getMatrizClave(j));
                        System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                        System.out.println("Edad: " + matriz.getMatrizEdad(j));
                        System.out.println("Estatura: " + matriz.getMatrizEstatura(j));
                        System.out.println("\n");
                    }
                    break;
            }//switch

        } while (opcion != 6);//while
    }

    public static boolean confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int respuesta;
        Scanner scar = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        respuesta = scar.nextInt();
        scar.skip("\n");
        return respuesta == 1; //Si es Verdadero acepta el cambio. Si es Falso, lo rechaza
    }

    public static boolean confirmarBaja() { //Metodo para confirmar el cambio de la modificacion de un campo
        int respuesta;
        Scanner scar = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar la baja a la persona?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        respuesta = scar.nextInt();
        scar.skip("\n");
        return respuesta == 1; //Si es Verdadero acepta el cambio. Si es Falso, lo rechaza
    }
}
