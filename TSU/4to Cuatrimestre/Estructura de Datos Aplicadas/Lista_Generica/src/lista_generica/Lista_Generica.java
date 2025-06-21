/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista_generica;

import java.util.ArrayList;
import java.util.*;

/**
 *
 * @author Luis
 */
public class Lista_Generica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner flor = new Scanner(System.in);
        ArrayList<String> listita = new ArrayList<>();
        int tecla = 0;
        boolean response = false;
        do {
            System.out.println("-------------MENU DE ARRAYLIST-------------------");
            System.out.println("Selecciona la opcion" +
                    "\n1.- Verificar si el ArrayList esta vacia o llena" +
                    "\n2.- Ingresar un nuevo elemento en el ArrayList" +
                    "\n3.- Mostrar los elementos del ArrayList" +
                    "\n4.- Devolver y Eliminar el primer elemento del ArrayList" +
                    "\n5.- Solo devolver el primer elemento del ArrayList" +
                    "\n6.- Mostrar los registros en String." +
                    "\n7.- Salir");
            System.out.print("Opcion: ");
            tecla = flor.nextInt();

            switch (tecla) {
                case 1:
                    System.out.println(estaVacia(listita, response));
                    System.out.println("Numero de elementos: "+listita.size()); //Visualiza el num de elementos del arrayList
                    break;
                case 2:
                    aniadir(listita);
                    break;
                case 3:
                    muestrodeArreglos(listita);
                    break;
                case 4:
                    extraer(listita);
                    break;
                case 5:
                    primero(listita);
                    break;
                case 6:
                    formaString(listita);
                    break;
                case 7:
                    System.out.println("Saliendo :)");
                    break;      
                default:
                    System.out.println("Opcion No Valida!");
            }
            System.out.println("\n");
        } while (tecla != 7);

    }

    //Opcion 1: Metodo que valida si el ArrayList tiene elemento o esta vacio
    public static boolean estaVacia(ArrayList listita, boolean response) {
        if(!listita.isEmpty()){ //isEmpty() valida si el arrayList tiene elementos en la cadena
            System.out.println("El Array tiene elementos uwu");
            response = true;
        } else {
            System.out.println("El Array esta Vacio unu");
            response = false;
        }
        return response;
    }

    //Opcion 2: Metodo que añade un objeto al final del ArrayList
    public static void aniadir(ArrayList listita) {
        Scanner chiquito = new Scanner(System.in);
        System.out.print("Introduce un elemento al ArrayList: ");
        String element = chiquito.nextLine();
        listita.add(element);
        System.out.println("Elemento agregado al ArrayList, Que Proooo Bv"); 
    }
    
    //Opcion 3: Metodo que muestra los elementos del arrayList
    public static void muestrodeArreglos(ArrayList listita) {
        if (!listita.isEmpty()) {
            System.out.println("-----ELEMENTOS DEL ARRAYLIST-------");
            for (int i = 0; i < listita.size(); i++) { 
                System.out.println("Posicion: "+ i + " , Elemento: " + listita.get(i));
            }
        } else {
            System.out.println("Error al mostrar el ArrayList, debes de llenarlo!");
        }
    }

    //Opcion 4: Metodo que devuelve y elimina el primer elemento del ArrayList
    public static void extraer(ArrayList listita) {  
        if (!listita.isEmpty()) {
            for(int i = 0; i == 0; i++) {
                System.out.println("La posicion del primer elemento (Posicion " + i + " ) es " + listita.get(0));
            }
            boolean cambio = eliminacion();
            if (cambio) {
               listita.remove(0);
               System.out.println("Se ha eliminado el primer elemento correctamente Siuuuuuuuu"); 
            } else {
                System.out.println("Error al eliminar el elemento!!!!"); 
            }
        } else {
            System.out.println("Error al mostrar el ArrayList, debes de llenarlo!");
        }

    }
    
    //Opcion 5: Metodo que solo devuelve el primer elemento del ArrayList
    public static void primero(ArrayList listita) {
        if (!listita.isEmpty()) {
            for(int i = 0; i == 0; i++) {
                System.out.println("La posicion del primer elemento (Posicion " + i + ") es " + listita.get(0));
            }
        } else {
            System.out.println("Error al mostrar el primer registro del ArrayList, debes de llenarlo!");
        }
    }
    
    //Opcion 6: Metodo que devuelve en forma de String la información del arrayList
    public static void formaString(ArrayList listita){
        System.out.println("El arrayList en String es ... "+listita.toString());
    }
    
    //Metodo para eliminar el primer elemento del ArrayList
    public static boolean eliminacion() { 
        int resp;
        Scanner messi = new Scanner(System.in);
        System.out.println("¿Estas seguro de eliminar el primer elemento?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = messi.nextInt();
        messi.skip("\n");
        return resp == 1; //Si es Verdadero acepta el cambio. Si es Falso, lo rechaza
    }
    
}
