/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnum;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestNum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        leer.useDelimiter("\n");

        //longitud del arreglo
        int longitud;

        //opcion menu
        int opc = 0;

        //numeros alta
        int numero = 0, nuevoNumero = 0;

        int indice = 0;

        arregloNumeros nn;

        //matriz
        int[] numeros = null;

        //auxiliar buscar numero
        int aux, aux2;

        //respuesta confirmacion dar de baja
        int resp;

        System.out.print("Cantidad de numeros: ");
        longitud = leer.nextInt();
        nn = new arregloNumeros(longitud);

        do {
            System.out.println("Menu\n"
                    + "1. Alta de un numero | "
                    + "2. Cambio de numero | "
                    + "3. Eliminar los numeros | "
                    + "4. Imprimir los numeros | "
                    + "5. Salir");

            System.out.print("¿Que accion desea realizar?: ");
            opc = leer.nextInt();
            switch (opc) {
                case 1:
                    if (nn.isHayEspacio()) {

                        System.out.print("Ingresa un numero: ");
                        numero = leer.nextInt();
                        aux = nn.buscar(numero);
                        if (aux == -1) {
                            nn.setNumero(nn.getIndex(), numero);
                            nn.incrementar();
                        } else {
                            System.out.println("Numero repetido");
                        }

                    } else {
                        System.out.println("ERROR... sin espacios disponibles");
                    }

                    break;

                case 2:

                    System.out.print("Ingresa el numero a cambiar: ");
                    numero = leer.nextInt();

                    indice = nn.buscar(numero);
                    if (indice != -1) {
                        System.out.println("Numero " + numero + " encontrado en la posicion " + (indice + 1));
                        do {

                            System.out.println("Ingresa el nuevo numero");
                            nuevoNumero = leer.nextInt();
                            aux2 = nn.buscar(nuevoNumero);
                            if (aux2 != -1) {
                                System.out.println("Numero repetido");
                                System.out.println("Numero " + nuevoNumero + " encontrado en la posicion " + (indice + 1));
                            } else {
                                nn.setNumero(indice, nuevoNumero);

                            }

                        } while (aux2 != -1 && aux2 != -2);
                    }
                    break;

                case 3:
                    System.out.print("Ingresa el Numero a eliminar: ");
                    numero = leer.nextInt();
                    indice = nn.buscar(numero);
                    if (indice != -1) {
                        System.out.println("Numero " + numero + " encontrado en la posicion " + (indice + 1));
                        System.out.print("¿Estas seguro de darlo de baja? 1 = si, 2 = no: ");
                        resp = leer.nextInt();
                        if (resp == 1) {
                            nn.eliminar(numero);
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < longitud; i++) {
                        System.out.println("Numero " + nn.getNumero(i) + " en la posicion " + (i + 1));

                    }
                    break;
                case 5:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("ERROR...ingresa una opcion valida");
                    break;
            }
        } while (opc != 5);
    }
    
}
