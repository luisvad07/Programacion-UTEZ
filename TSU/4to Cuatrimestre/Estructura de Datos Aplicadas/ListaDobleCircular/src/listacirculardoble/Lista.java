/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package listacirculardoble;

import java.util.Scanner;

/**
 *
 * @author carsi
 */
public class Lista {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        ListaEnlazadaDoble lista = new ListaEnlazadaDoble();
        
        int elec;
        do {
            System.out.println("---- LISTA CIRCULAR DOBLE ----");
            System.out.println("Selecciona una opción: ");
            System.out.println("1. Insertar");
            System.out.println("2. Eliminar");
            System.out.println("3. Consultar");
            System.out.println("4. Modificar");
            System.out.println("5. Mostrar Nodos");
            System.out.println("6. Salir");
            System.out.print("Opcion: ");
            elec = s.nextInt();
            System.out.println("\n");
            switch (elec) {
                case 1:
                    System.out.println("Agregar Nodo....");
                    System.out.print("Ingresa el dato para el nodo: ");
                    lista.agregar(s.nextInt());
                    break;
                case 2:
                    System.out.print("Numero que deseas eliminar: ");
                    int n = s.nextInt();
                    if (confirmarCambio()) {
                        lista.eliminarPos(n);
                        System.out.println("Numero " + n + " eliminado correctamente!");
                    } else {
                        System.out.println("Cambios no guardados!...");
                    }
                    break;
                case 3:
                    System.out.print("Numero que deseas buscar: ");
                    int busq = s.nextInt();
                    if (lista.contiene(busq)) {
                        System.out.println("Numero " + busq + " existe!");
                    } else {
                        System.out.println("Error al Encontrar el Numero!");
                    }
                    break;
                case 4:
                    System.out.print("Posición que deseas buscar: ");
                    int pos = s.nextInt();
                    System.out.print("Numero que deseas reemplazar: ");
                    int num = s.nextInt();
                    if (confirmarCambio()) {
                        lista.actualizarDato(pos, num);
                        lista.imprimirLista();
                        System.out.println("Numero reemplazado correctamente!");
                    } else {
                        System.out.println("Cambios no guardados!...");
                    }        
                    break;    
                case 5:
                    lista.imprimirLista();
                    break; 
                case 6:
                    System.out.println("Saliendo....");    
                    break;     
                default:
                    throw new AssertionError();
            }
            System.out.println("\n");
        } while (elec != 6);
    }
    
    public static boolean confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1;
    }
}
