/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpersona;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
/*Equipo 1: Jose Eduardo Arroyo Palafox y Luis Eduardo Bahena Castillo*/
public class TestPersona {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");
        boolean change;

        String nombre;
        int edad, opc;
        double estatura;
        char sexo;
        boolean estadoCivil;

        Persona objPersona;

        int elec;
        System.out.println("------Formulario de Ingreso de Datos--------");
        System.out.println("Ingrese su nombre");
        nombre = teclado.next();

        System.out.println("Ingrese su edad");
        edad = teclado.nextInt();

        System.out.println("Ingrese su estatura");
        estatura = teclado.nextDouble();

        System.out.println("Ingrese su sexo");
        sexo = teclado.next().charAt(0);

        System.out.println("Seleccione su Estado Civil: 1.- Casado / 2.- Soltero");
        opc = teclado.nextInt();
        estadoCivil = (opc == 1); //1 = verdadero, 2 = falso;
        // estadoCivil = aux==1?true:false;

        objPersona = new Persona(nombre, edad, estatura, sexo, estadoCivil);//Llamada a los metodos

        do {
            System.out.println("¿Que accion desea efectuar?");
            System.out.println("1.- Mostrar los Datos\n"
                    + "2.- Cambiar Datos\n"
                    + "3.- Salir");
            elec = teclado.nextInt();
            switch (elec) {
                case 1: // Mostrar Datos Ingresados
                    System.out.println("---------DATOS DE LA PERSONA---------");
                    System.out.println("Su nombre es: " + objPersona.getNombre());
                    System.out.println("Su edad es: " + objPersona.getedad());
                    System.out.println("Su estatura es: " + objPersona.getEstatura());
                    System.out.println("Su sexo es: " + objPersona.getSexo());
                    System.out.println("Su estado civil es: " + ((objPersona.isestadoCivil()) ? "Casado" : "Soltero"));
                    break;
                case 2: // Modificar datos a partir de un Submenu
                    int choix;
                    do {
                        System.out.println("----------CAMBIAR DATOS DE LA PERSONA---------");
                        System.out.println("Selecciona la operacion que desea efectuar:");
                        System.out.println("1.- Modificar nombre\n"
                                + "2.- Modificar edad\n"
                                + "3.- Modificar estatura\n"
                                + "4.- Modificar sexo\n"
                                + "5.- Modificar Estado Civil\n"
                                + "6.- Salir");
                        choix = teclado.nextInt();
                        teclado.skip("\n");
                        switch (choix) {
                            case 1: //Modificar Nombre
                                System.out.println("Ingresa tu nuevo nombre:");
                                nombre = teclado.next();
                                change = confirmarCambio();
                                if (change) {
                                    objPersona.setNombre(nombre);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 2: // Modificar Edad
                                System.out.println("Ingresa tu nueva edad:");
                                edad = teclado.nextInt();
                                change = confirmarCambio();
                                if (change) {
                                    objPersona.setEdad(edad);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 3: //Modificar Estatura
                                System.out.println("Ingresa tu nueva estatura:");
                                estatura = teclado.nextDouble();
                                change = confirmarCambio();
                                if (change) {
                                    objPersona.setEstatura(estatura);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 4: //Modificar Sexo
                                System.out.println("Ingresa tu nuevo sexo:");
                                sexo = teclado.next().charAt(0);
                                change = confirmarCambio();
                                if (change) {
                                    objPersona.setSexo(sexo);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 5:// Modificar Estado Civil
                                System.out.println("Selecciona nuevamente el estado civil...");
                                System.out.println("1.- Casado");
                                System.out.println("2.- Soltero");
                                opc = teclado.nextInt();
                                estadoCivil = (opc == 1); //1 = verdadero, 0 = falso;
                                change = confirmarCambio();
                                if (change) {
                                    objPersona.setEstadoCivil(estadoCivil);
                                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                                } else {
                                    System.out.println("Cambios no guardados!...");
                                }
                                break;
                            case 6: //Regresar al menu principal
                                System.out.println("Regresando a menu principal...");
                                break;
                            default:
                                System.out.println("Error de Opcion del submenu!");
                                break;
                        }
                    } while (choix != 6);
                    break;
                case 3: //Salir del programa
                    System.out.println("Saliendo... GOOD BYE!");
                    break;
                default:
                    System.out.println("Error de Operacion!");
                    break;
            }
        } while (elec != 3);
    }

    public static boolean confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1; //Si es Verdadero acepta el cambio, Si es Falso lo rechaza
    }
}
