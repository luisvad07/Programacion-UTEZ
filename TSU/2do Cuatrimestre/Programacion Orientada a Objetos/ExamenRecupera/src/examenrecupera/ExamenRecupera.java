/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenrecupera;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class ExamenRecupera {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Metodo para leer desde teclado
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");

        //Llamada de la clase MotDePasse para validar la contraseÃ±a
        ValidarContra key = new ValidarContra();

        //Llama la clase Arreglos para gestionar los arreglos
        ControlaArreglos obj;

        //Variables para validar la contraseÃ±a
        String clavier;
        boolean pass = false;

        //Variables para la longitud del arreglo de acuerdo a los tipos de socios
        int programacion = 0, matematicas = 0;

        //Variables para realizar bajas de registros
        String leerClave;
        boolean baja;

        //Variables para leer datos
        String nombre = null, clave = null;
        int curso = 0, day;

        //Variable para hacer la busqueda de la matricula
        int auxt = -1;

        //Variables para leer las opciones de los switches
        int opcion = 0, submenu = 0, submenu2, submenuimpresiones;

        //Variables para mostrar los precios de las inscripciones de los cursos
        double PRECIOPROGRAMACION = 2000.0, PRECIOMATE = 1500.0;
        
        //Variables para cmabiar los costos de los cursos
        double newpagoProg, newPagoMate;

        //Variables para calcular la totalizacion de pagos y acumulador del dia
        double totalPagar = 0, acumPago = 0;

        //Variables para imprimir y calcular descuentos
        double valorDesc;
        final double DESC1 = 0.15, DESC2 = 0.10, DESC3 = 0.5, REEMBOLSO = 0.6;

        //Variables de ID de los cursos
        String curso1 = "prog1";
        String curso2 = "mate1";

        System.out.println("--------- Bienvenido al sistema de Cine en Casa ---------");
        for (int i = 0; i < 3 && !pass; i++) {

            System.out.println("Ingresa la contraseña (Tienes 3 intentos)");
            System.out.print("Intento " + (i + 1) + ": ");
            clavier = teclado.next();

            pass = key.Acceso(clavier);

            if (pass == true) {
                System.out.println("CONTRASEÑA CORRECTA");
                System.out.println("\n");
                System.out.print("¿Cuantos lugares se tendran para el curso de programacion?: ");
                programacion = teclado.nextInt();
                System.out.print("¿Cuantos lugares se tendran para el curso de matematicas?: ");
                matematicas = teclado.nextInt();
                int longitud = programacion + matematicas;
                obj = new ControlaArreglos(programacion, matematicas);

                System.out.println("========================================================");
                System.out.println("               BIENVENIDO");
                System.out.println("========================================================");
                do {
                    System.out.println("MENU");
                    System.out.println("1.- Registro de la persona\n"
                            + "2.- Cambio del costo del curso\n"
                            + "3.- Cancelacion del curso a la persona\n"
                            + "4.- Imprimir cursos vendidos\n"
                            + "5.- Salir");

                    System.out.print("Seleccione la opcion a realizar: ");
                    opcion = teclado.nextInt();

                    switch (opcion) {
                        case 1: //Opcion Registrar
                            if (obj.getSiguienteVacio() < longitud) {
                                do {
                                    System.out.println("\n");
                                    System.out.println("MENU");
                                    System.out.println("1.- Programacion\n"
                                            + "2.- Matematicas\n"
                                            + "3.- Regresar al Menu principal");

                                    System.out.print("Seleccione la opcion a realizar: ");
                                    submenu = teclado.nextInt();
                                    switch (submenu) {
                                        case 1:
                                            if (obj.getProgramacion() > 0) {
                                                System.out.println("\n");
                                                System.out.println("Registro exitoso");
                                                do {
                                                    System.out.print("Ingresa tu clave: ");
                                                    clave = teclado.next();
                                                    auxt = obj.busqueda(clave);
                                                    if (auxt == -1) {
                                                        System.out.println("Clave " + clave + " guardada exitosamente");
                                                    } else {
                                                        System.err.println("Ya existe esa matricula, Favor de Poner otra clave!");
                                                    }
                                                } while (auxt != -1 && auxt != -2);
                                                System.out.print("Ingresa tu nombre: ");
                                                nombre = teclado.next();

                                                System.out.println("ID de Curso: " + curso1);

                                                System.out.println("El precio de Inscripcion es de: " + PRECIOPROGRAMACION);
                                                curso = submenu; // El curso de programacion con ID: prog1
                                                obj.registrar(clave, nombre, curso);

                                                System.out.print("Ingresa el dia de inscripcion del mes: ");
                                                day = teclado.nextInt();

                                                if (day > 1 || day < 5) {
                                                    System.out.println("Usted tiene un descuento del 15%");
                                                    valorDesc = PRECIOPROGRAMACION * DESC1;
                                                    totalPagar = PRECIOPROGRAMACION - valorDesc;
                                                } else if (day > 5 || day < 10) {
                                                    System.out.println("Usted tiene un descuento del 10%");
                                                    valorDesc = PRECIOPROGRAMACION * DESC2;
                                                    totalPagar = PRECIOPROGRAMACION - valorDesc;
                                                } else if (day > 10 || day < 15) {
                                                    System.out.println("Usted tiene un descuento del 5%");
                                                    valorDesc = PRECIOPROGRAMACION * DESC3;
                                                    totalPagar = PRECIOPROGRAMACION - valorDesc;
                                                } else {
                                                    System.out.println("Usted no tiene descuento");
                                                    totalPagar = PRECIOPROGRAMACION;
                                                }

                                                System.out.println("Total a pagar: " + totalPagar);
                                                acumPago += totalPagar;
                                            } else {
                                                System.out.println("ERROR al volver a registrar!, Ya no hay cupos!");
                                            } 
                                            break;
                                        case 2:
                                            if (obj.getMatematicas() > 0) {
                                                System.out.println("\n");
                                                System.out.println("Registro exitoso");
                                                do {
                                                    System.out.print("Ingresa tu clave: ");
                                                    clave = teclado.next();
                                                    auxt = obj.busqueda(clave);
                                                    if (auxt == -1) {
                                                        System.out.println("Clave " + clave + " guardada exitosamente");
                                                    } else {
                                                        System.out.println("Ya existe esa matricula, Favor de Poner otra clave!");
                                                    }
                                                } while (auxt != -1 && auxt != -2);
                                                System.out.print("Ingresa tu nombre: ");
                                                nombre = teclado.next();

                                                System.out.println("ID de Curso: " + curso2);

                                                curso = submenu; // El curso de matematicas con ID: mate1
                                                obj.registrar(clave, nombre, curso);

                                                System.out.print("Ingresa el dia de inscripcion del mes: ");
                                                day = teclado.nextInt();

                                                if (day > 1 || day < 5) {
                                                    System.out.println("Usted tiene un descuento del 15%");
                                                    valorDesc = PRECIOMATE * DESC1;
                                                    totalPagar = PRECIOMATE - valorDesc;
                                                } else if (day > 5 || day < 10) {
                                                    System.out.println("Usted tiene un descuento del 10%");
                                                    valorDesc = PRECIOMATE * DESC2;
                                                    totalPagar = PRECIOMATE - valorDesc;
                                                } else if (day > 10 || day < 15) {
                                                    System.out.println("Usted tiene un descuento del 5%");
                                                    valorDesc = PRECIOMATE * DESC3;
                                                    totalPagar = PRECIOMATE - valorDesc;
                                                } else {
                                                    System.out.println("Usted no tiene descuento");
                                                    totalPagar = PRECIOMATE;
                                                }

                                                System.out.println("Total a pagar: " + totalPagar);
                                                acumPago += totalPagar;
                                            } else {
                                                System.out.println("ERROR al volver a registrar!, Ya no hay cupos!");
                                            }
                                            break;
                                        case 3:
                                            System.out.println("Regresando.....");
                                            break;
                                        default:
                                            System.out.println("Opcion Erronea");
                                            break;
                                    }
                                } while (submenu != 3);
                            }
                            System.out.println("\n");
                            break;

                        case 2: //Opcion Cambio del costo
                            do {
                                System.out.println("\n");
                                System.out.println("MENU");
                                System.out.println("1.- Programacion\n"
                                        + "2.- Matematicas\n"
                                        + "3.- Regresar al Menu principal");
                                System.out.print("Seleccione la opcion a realizar: ");
                                submenu2 = teclado.nextInt();
                                switch (submenu2) {
                                    case 1:
                                        System.out.print("Ingresa el nuevo costo del curso de programacion: ");
                                        newpagoProg = teclado.nextDouble();
                                        if (newpagoProg < PRECIOPROGRAMACION) {
                                            System.out.println("No se puede cambiar el costo ya que el costo es menor!");
                                        } else {
                                            if (confirmarCambio() == 1) {
                                                PRECIOPROGRAMACION = newpagoProg;
                                                System.out.println("Cambio exitoso");
                                                System.out.println("El costo del curso de programacion ha cambiado a $" + PRECIOPROGRAMACION);
                                            } else {
                                                System.out.println("ERROR... cambios no realizados");
                                            }
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Ingresa el nuevo costo del curso de matematicas: ");
                                        newPagoMate = teclado.nextDouble();
                                        if (newPagoMate < PRECIOMATE) {
                                            System.out.println("No se puede cambiar el costo ya que el costo es menor!");
                                        } else {
                                            if (confirmarCambio() == 1) {
                                                PRECIOMATE = newPagoMate;
                                                System.out.println("Cambio exitoso");
                                                System.out.println("El costo del curso de matematicas ha cambiado a $" + PRECIOMATE);
                                            } else {
                                                System.out.println("ERROR... cambios no realizados");
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Regresando.....");
                                        break;
                                    default:
                                        System.out.print("Opcion Erronea");
                                        break;
                                }
                            } while (submenu2 != 3);

                            System.out.println("\n");
                            break;
                        case 3: // Opcion Cancelacion del curso a la persona 
                            if (obj.getSiguienteVacio() != 0) {
                                System.out.println("\n");
                                System.out.print("Ingresa la clave de la persona: ");
                                leerClave = teclado.next();
                                auxt = obj.busqueda(leerClave);
                                if (auxt != -1) {
                                    System.out.println("Se dara de baja a la persona: " + obj.getClave(auxt));
                                    baja = confirmarBaja();
                                    if (baja) {
                                        obj.baja(leerClave);
                                        obj.disminuirSiguienteVacio();
                                        System.out.println("Reembolso del 60% en tu pago");
                                        double valor = totalPagar * REEMBOLSO;
                                        System.out.println("Se te regresa " + valor);
                                        acumPago -= valor;
                                        System.out.println("Se ha dado de baja a la persona correctamente");
                                    } else {
                                        System.out.println("Error al realizar la baja!");
                                    }
                                }
                            } else {
                                System.out.println("No hay Registros!");
                            }
                            System.out.println("\n");
                            break;
                        case 4: // Opcion Imprimir cursos vendidos
                            do {
                                System.out.println("\n");
                                System.out.println("MENU");
                                System.out.println("1.- Programacion\n"
                                        + "2.- Matematicas\n"
                                        + "3.- Regresar al Menu principal");
                                System.out.print("Seleccione la opcion a realizar: ");
                                submenuimpresiones = teclado.nextInt();
                                switch (submenuimpresiones) {
                                    case 1:
                                        for (int j = 0; j < obj.getSiguienteVacio(); j++) {
                                            if (obj.getCurso(j) == 1) {
                                                System.out.println("\n");
                                                System.out.println("Venta Programacion # " + (j + 1));
                                                System.out.println("Clave: " + obj.getClave(j));
                                                System.out.println("Nombre: " + obj.getNombre(j));
                                                System.out.println("Curso: " + obj.getCurso(j));
                                                System.out.println("ID de Curso: " + curso1);
                                            }//if
                                        }//for
                                        break;
                                    case 2:
                                        for (int j = 0; j < obj.getSiguienteVacio(); j++) {
                                            if (obj.getCurso(j) == 2) {
                                                System.out.println("\n");
                                                System.out.println("Venta Matematicas # " + (j + 1));
                                                System.out.println("Clave: " + obj.getClave(j));
                                                System.out.println("Nombre: " + obj.getNombre(j));
                                                System.out.println("Curso: " + obj.getCurso(j));
                                                System.out.println("ID de Curso: " + curso2);
                                            }//if
                                        }//for
                                        break;
                                    case 3:
                                        System.out.println("Regresando.....");
                                        break;
                                    default:
                                        System.out.print("Opcion Erronea");
                                        break;
                                }
                            } while (submenuimpresiones != 3);

                            System.out.println("\n");
                            break;
                        case 5:
                            System.out.println("Total de ventas el dia de hoy: " + acumPago);
                            break;
                        default:
                            System.out.println("La opcion que selecciono es erronea!");
                            break;
                    }

                } while (opcion != 5);
            }
        }
        if (pass == false) {
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

    public static boolean confirmarBaja() { //Metodo para confirmar la baja de un registro
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
