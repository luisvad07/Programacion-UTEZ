/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnumerosenteros;

import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class TestNumerosEnteros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        NumerosArreglos objeto;
        Scanner teclado = new Scanner(System.in);
        teclado.useDelimiter("\n");

        int longitud, opc, indice = 0, numero = 0;

        System.out.print("Ingresa la cantidad de numeros: ");
        longitud = teclado.nextInt();
        objeto = new NumerosArreglos(longitud);
        do {
            System.out.println("\n");
            System.out.println("-----MENU-------");
            System.out.println("1.- Alta de un número");
            System.out.println("2.- Búsqueda de número");
            System.out.println("3.- Imprimir los números");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opcion: ");
            opc = teclado.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    if (objeto.hayEspacio()) {
                        System.out.print("Ingresa un numero: ");
                        numero = teclado.nextInt();
                        objeto.setNumero(numero);
                    } else {
                        System.out.println("Ya no se pueden agregar los numeros");
                    }
                    break;
                case 2:
                    System.out.print("Ingresa un numero a buscar: ");
                    numero = teclado.nextInt();
                    do {
                        indice = objeto.buscar(numero);
                        if (indice != -1) {
                            System.out.println("Numero " + numero + " encontrado en la posicion " + indice);
                            break;
                        }
                    } while (indice != -1 && indice != -2);
                    System.out.println("Fin de Busqueda");
                    break;
                case 3:
                    System.out.println("---IMPRESION DE LOS NUMEROS----");
                    for (int i = 0; i < objeto.longitud(); i++) {
                        System.out.println("Numero " + objeto.getNumero(i) + " en la posicion " + i);
                    }
                    break;
                case 4:
                    System.out.println("\n");
                    System.out.println("Hasta Luego....");
                    break;
            }
        } while (opc != 4);

    }

}
