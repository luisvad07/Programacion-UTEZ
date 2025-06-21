/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu3;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestExamenU3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Metodo para leer desde teclado
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");

        //Llamada de la clase MotDePasse para validar la contraseña
        MotDePasse acces = new MotDePasse();

        //Llama la clase Arreglos para gestionar los arreglos
        Arreglos matriz;

        //Variables para validar la contraseña
        String clavier;
        boolean pass = false;

        //Variables para la longitud del arreglo de acuerdo a los tipos de socios
        int longitudSocioNormal = 0, longitudSocioPlus = 0;

        //Variable auxiliar para ayudarnos al indice
        int ap = -1;

        //Variables para realizar bajas de registros
        String leerClave;
        boolean baja;

        //Variables para leer datos
        String nombre = null, clave = null;
        int tipoSocio = 0;
        int submenu;

        //Variable para hacer la busqueda de la matricula
        int auxt = -1;

        //Variables para leer las opciones de los switches
        int opcion = 0, pelicula, pelisocio;

        //Variables para mostrar los precios de las inscripciones de las peliculas
        final double PRECIONORMAL = 100.0, PRECIOPLUS = 200.0;

        //Variables para calcular la totalizacion de pagos
        double totalPagarNormal = 0, totalPagarPlus = 0, acumPago = 0, totalFinal = 0.0;

        //Variables para imprimir y calcular descuentos
        double valorDescuentoEfec;
        final double EFECTIVODESC = 0.05, COMISION = 0.3, PELINORMAL = 30.0, PELIPLUS = 20.0;

        System.out.println("--------- Bienvenido al sistema de Cine en Casa ---------");
        for (int i = 0; i < 3 && !pass; i++) {

            System.out.println("Ingresa la contraseña (Tienes 3 intentos)");
            System.out.print("Intento " + (i + 1) + ": ");
            clavier = teclado.next();

            pass = acces.cocher(clavier);

            if (pass == true) {
                System.out.println("CONTRASEÑA CORRECTA");
                System.out.println("\n");
                System.out.print("¿Cuantos socios Normales se tendrán?: ");
                longitudSocioNormal = teclado.nextInt();
                System.out.print("¿Cuantos socios Plus se tendrán?: ");
                longitudSocioPlus = teclado.nextInt();
                int longitud = longitudSocioNormal + longitudSocioPlus; //Suma las longitudes para calcularlos y validar los registros
                matriz = new Arreglos(longitudSocioNormal, longitudSocioPlus);

                System.out.println("========================================================");
                System.out.println("               BIENVENIDO");
                System.out.println("========================================================");
                do {
                    System.out.println("MENU");
                    System.out.println("1.- Registrar un socio\n"
                            + "2.- Renta de una película\n"
                            + "3.- Baja de un socio\n"
                            + "4.- Imprimir socios\n"
                            + "5.- Salir");

                    System.out.print("Seleccione la opcion a realizar: ");
                    opcion = teclado.nextInt();

                    switch (opcion) {
                        case 1: //Opcion Registrar
                            if (matriz.getSiguienteVacio() < longitud) {
                                System.out.println("\n");
                                do {
                                    System.out.println("¿Que tipo de socio se registrará?");
                                    System.out.println("1.- Normal\n"
                                            + "2.- Plus");
                                    System.out.print("Escoje una opcion: ");
                                    tipoSocio = teclado.nextInt();
                                    if (tipoSocio < 1 || tipoSocio > 2) {
                                        System.out.println("Opcion Erronea, vuelva a digitar el tipo de socio!");
                                    }
                                } while (tipoSocio < 1 || tipoSocio > 2);
                                switch (tipoSocio) {
                                    case 1:
                                        if (matriz.getNormal() > 0) {
                                            System.out.println("Registro exitoso");
                                            do {
                                                System.out.print("Ingresa tu clave: ");
                                                clave = teclado.next();
                                                auxt = matriz.buscar(clave);
                                                if (auxt == -1) {
                                                    System.out.println("Clave guardada exitosamente");
                                                } else {
                                                    System.out.println("Ya existe esa matricula, Favor de Poner otra clave!");
                                                }
                                            } while (auxt != -1 && auxt != -2);
                                            System.out.print("Ingresa tu nombre: ");
                                            nombre = teclado.next();
                                            matriz.registrar(clave, nombre, tipoSocio);
                                            if (tipoSocio == 1) {
                                                System.out.println("El precio de Inscripcion es de: " + PRECIONORMAL);
                                                acumPago += PRECIONORMAL;
                                            }       
                                        } else {
                                            System.out.println("ERROR al volver a registrar!, Ya no hay cupos!");
                                        }
                                        break;
                                    case 2:
                                        if (matriz.getPlus() > 0) {
                                            System.out.println("Registro exitoso");
                                            do {
                                                System.out.print("Ingresa tu clave: ");
                                                clave = teclado.next();
                                                auxt = matriz.buscar(clave);
                                                if (auxt == -1) {
                                                    System.out.println("Clave guardada exitosamente");
                                                } else {
                                                    System.out.println("Ya existe esa matricula, Favor de Poner otra clave!");
                                                }
                                            } while (auxt != -1 && auxt != -2);
                                            System.out.print("Ingresa tu nombre: ");
                                            nombre = teclado.next();
                                            matriz.registrar(clave, nombre, tipoSocio);
                                            if (tipoSocio == 2) {
                                                System.out.println("El precio de Inscripcion es de: " + PRECIOPLUS);
                                                acumPago += PRECIOPLUS;
                                            }    
                                        } else {
                                            System.out.println("ERROR al volver a registrar!, Ya no hay cupos!");
                                        }
                                        break;
                                }
                            } else {
                                System.err.println("Ya no hay cupos!");
                            }
                            System.out.println("\n");
                            break;
                        case 2: //Opcion Rentar peliculas
                            if (matriz.getSiguienteVacio() != 0) {
                                System.out.print("Ingresa la clave de la persona a buscar: ");
                                leerClave = teclado.next();
                                auxt = matriz.buscar(leerClave);
                                if (auxt != -1) {
                                    System.out.println("\n");
                                    System.out.println("Precios");
                                    System.out.println("Tipo de Socio 1: Normal, Costo de la renta de pelicula: $30\n"
                                            + "Tipo de Socio 2: Plus, Costo de la renta de pelicula: $20");
                                    System.out.print("¿Cuantas peliculas desea adquirir? (De acuerdo a tu tipo de socio): ");
                                    pelisocio = teclado.nextInt();
                                    if (tipoSocio == 1) {
                                        System.out.println("Peliculas adquiridas: " + pelisocio);
                                        totalPagarNormal = pelisocio * PELINORMAL;
                                        System.out.println("Total a pagar: " + totalPagarNormal);
                                    } else {
                                        if (tipoSocio == 2) {
                                            System.out.println("Peliculas adquiridas: " + pelisocio);
                                            totalPagarPlus = pelisocio * PELIPLUS;
                                            System.out.println("Total a pagar: " + totalPagarPlus);
                                        }
                                    }
                                    System.out.println("\n");
                                    System.out.println("SUBMENU");
                                    System.out.println("1.- Efectivo\n"
                                            + "2.- Tarjeta de Débito\n"
                                            + "3.- Tarjeta de Crédito");
                                    System.out.print("Seleccione la opcion a realizar: ");
                                    pelicula = teclado.nextInt();
                                    switch (pelicula) {
                                        case 1:
                                            if (tipoSocio == 1) {
                                                System.out.println("Usted tiene un descuento del 5%");
                                                valorDescuentoEfec = totalPagarNormal * EFECTIVODESC;
                                                totalFinal = totalPagarNormal - valorDescuentoEfec;
                                                System.out.println("Total a pagar con descuento: " + totalFinal);
                                                acumPago += totalFinal;
                                                break;
                                            } else {
                                                if (tipoSocio == 2) {
                                                    System.out.println("Usted tiene un descuento del 5%");
                                                    valorDescuentoEfec = totalPagarPlus * EFECTIVODESC;
                                                    totalFinal = totalPagarPlus - valorDescuentoEfec;
                                                    System.out.println("Total a pagar con descuento: " + totalFinal);
                                                    acumPago += totalFinal;
                                                    break;
                                                }
                                            }
                                        case 2:
                                            if (tipoSocio == 1) {
                                                System.out.println("Usted no tiene descuento ni aumento");
                                                totalFinal = totalPagarNormal;
                                                acumPago += totalFinal;
                                                System.out.println("Total a pagar: " + totalFinal);
                                            } else {
                                                if (tipoSocio == 2) {
                                                    System.out.println("Usted no tiene descuento ni aumento");
                                                    totalFinal = totalPagarPlus;
                                                    acumPago += totalFinal;
                                                    System.out.println("Total a pagar: " + totalFinal);
                                                }
                                            }
                                            break;
                                        case 3:
                                            if (tipoSocio == 1) {
                                                System.out.println("Se le cobrara una comisión del 3%");
                                                totalFinal = totalPagarNormal + (totalPagarNormal * COMISION);
                                                acumPago += totalFinal;
                                                System.out.println("Total a pagar con comision: " + totalFinal);
                                            } else {
                                                if (tipoSocio == 2) {
                                                    System.out.println("Se le cobrara una comisión del 3%");
                                                    totalFinal = totalPagarPlus + (totalPagarPlus * COMISION);
                                                    acumPago += totalFinal;
                                                    System.out.println("Total a pagar con comision: " + totalFinal);
                                                }
                                            }
                                            break;
                                        default:
                                            System.err.println("Opcion Erronea!");
                                    }
                                }
                            }
                            break;
                        case 3: //Opcion Dar de Baja
                            if (matriz.getSiguienteVacio() != 0) {
                                System.out.println("\n");
                                System.out.println("Se dara de baja a la persona: " + matriz.getMatrizClave(ap));
                                baja = confirmarBaja();
                                if (baja) {
                                    matriz.eliminado(clave);
                                    ap--;
                                    System.out.println("Se ha dado de baja a la persona correctamente");
                                } else {
                                    System.err.println("Error al realizar la baja!");
                                }
                            } else {
                                System.err.println("No hay Registros");
                            }
                            System.out.println("\n");
                            break;
                        case 4:  //Opcion Imprimir Socios
                            System.out.println("SUBMENU");
                            System.out.println("1.- Imprimir socios Normal\n"
                                    + "2.- Imprimir socios Plus\n"
                                    + "3.- Imprimir todos los socios");
                            System.out.print("Seleccione la opcion a realizar: ");
                            submenu = teclado.nextInt();
                            switch (submenu) {
                                case 1: //Imprimir Socios Normal
                                    for (int j = 0; j < matriz.getSiguienteVacio(); j++) {
                                        if (matriz.getMatrizSocios(j) == 1) {
                                            System.out.println("\n");
                                            System.out.println("Persona # " + (j + 1));
                                            System.out.println("Clave: " + matriz.getMatrizClave(j));
                                            System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                                            System.out.println("Tipo de Socio: " + matriz.getMatrizSocios(j));
                                            System.out.println("\n");
                                        }//if
                                    }//for
                                    break;
                                case 2: //Imprimir Socios Plus
                                    for (int j = 0; j < matriz.getSiguienteVacio(); j++) {
                                        if (matriz.getMatrizSocios(j) == 2) {
                                            System.out.println("\n");
                                            System.out.println("Persona # " + (j + 1));
                                            System.out.println("Clave: " + matriz.getMatrizClave(j));
                                            System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                                            System.out.println("Tipo de Socio: " + matriz.getMatrizSocios(j));
                                            System.out.println("\n");
                                        }//if
                                    }//for
                                    break;
                                case 3: //Imprimir todos los Socios
                                    for (int j = 0; j < matriz.getSiguienteVacio(); j++) {
                                        System.out.println("\n");
                                        System.out.println("Persona # " + (j + 1));
                                        System.out.println("Clave: " + matriz.getMatrizClave(j));
                                        System.out.println("Nombre: " + matriz.getMatrizNombre(j));
                                        System.out.println("Tipo de Socio: " + matriz.getMatrizSocios(j));
                                        System.out.println("\n");
                                    }
                                    break;
                            }
                            break;
                        case 5: // Opcion Salir
                            System.out.println("Total a pagar el dia de hoy: " + acumPago); //Muestra la cantidad de ventas generadas
                            break;
                        default: //Otra Opcion
                            System.err.println("La opcion que selecciono es erronea!");
                            break;
                    }
                } while (opcion != 5);
            }
        }
        if (pass == false) {
            System.out.println("\n");
            System.err.println("Has superado el limite de intentos. No puedes ingresar al sistema!");
        }
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
