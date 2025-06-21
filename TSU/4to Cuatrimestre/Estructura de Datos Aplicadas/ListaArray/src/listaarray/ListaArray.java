/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaarray;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class ListaArray {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner flor = new Scanner(System.in);
        int tecla = 0;
        boolean response = false;
        int opcion, slv = 0, cont = 0;
        System.out.print("Introduce la longitud que tendra el array: ");
        slv = flor.nextInt();
        controlArray arreglo = new controlArray(slv);
        do {
            System.out.println("-------------MENU DE ARRAYLIST-------------------");
            System.out.println("Selecciona la opcion" +
                    "\n1.- Verificar si el Array esta vacia o llena" +
                    "\n2.- Ingresar un nuevo elemento en el Array" +
                    "\n3.- Mostrar los elementos del Array" +
                    "\n4.- Devolver y Eliminar el primer elemento del Array" +
                    "\n5.- Solo devolver el primer elemento del ArrayList" +
                    "\n6.- Mostrar los registros en String." +
                    "\n7.- Salir");
            System.out.print("Opcion: ");
            tecla = flor.nextInt();

            switch (tecla) {
                case 1:
                    System.out.println(estaVacia(arreglo, response));
                    System.out.println("Numero de posiciones del arreglo : "+arreglo.longt());
                    break;
                case 2:
                    aniadir(arreglo);
                    break;
                case 3:
                    muestrodeArreglos(arreglo);
                    break;
                case 4:
                    extraer(arreglo);
                    break;
                case 5:
                    primero(arreglo);
                    break;
                case 6:
                    formaString(arreglo);
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
    public static boolean estaVacia(controlArray arreglo, boolean response) {
        for (int i = 0; i < arreglo.longt(); i++) {
            if(arreglo.get(i) == null){
                System.out.println("El Array esta Vacio unu");
                response = false;
                break;
            } else {
                System.out.println("El Array tiene elementos uwu");
                response = true;
                break;
            }
        }
        return response;
    }

    //Opcion 2: Metodo que añade un objeto al final del ArrayList
    public static void aniadir(controlArray arreglo) {
        Scanner chiquito = new Scanner(System.in);
        chiquito.useDelimiter("\n");
        for (int i = 0; i < arreglo.longt(); i++) {
            if(arreglo.get(i) == null){
               System.out.println("Elige el tipo de dato para ingresar los elementos del array" +
                    "\n1.- String" +
                    "\n2.- Int" +
                    "\n3.- Double");
                System.out.print("Opcion: ");
                int respuesta = chiquito.nextInt();
                switch (respuesta) {
                    case 1:
                        for (int j = 0; j < arreglo.longt(); j++) {
                            System.out.print("Ingresa el elemento " + j+" : ");
                            String LEString = chiquito.next();
                            arreglo.set(j, LEString);
                            System.out.println("El elemento " + arreglo.get(j) + " fue añadido correctamente");
                        }
                        break;
                    case 2:
                        for (int j = 0; j < arreglo.longt(); j++) {
                            System.out.print("Ingresa el elemento " + j +" : ");
                            int LEInt = chiquito.nextInt();
                            arreglo.set(j, LEInt);
                            System.out.println("El elemento " + arreglo.get(j) + " fue añadido correctamente");
                        }
                        break;
                    case 3:
                        for (int j = 0; j < arreglo.longt(); j++) {
                            System.out.print("Ingresa el elemento " + j+" : ");
                            double LEDouble = chiquito.nextDouble();
                            arreglo.set(j, LEDouble);
                            System.out.println("El elemento " + arreglo.get(j) + " fue añadido correctamente");
                        }
                        break;
                    default:
                        System.out.println("Opcion No Valida mi estimado!!!!");
                        break;
                } 
            }
        }
    }
    
    //Opcion 3: Metodo que muestra los elementos del arrayList
    public static void muestrodeArreglos(controlArray arreglo) {
        System.out.println("-----ELEMENTOS DEL ARRAY-------");
        for (int i = 0; i < arreglo.longt(); i++) {
            System.out.println("Posicion: "+ i + " , Elemento: " + arreglo.get(i));
            if (arreglo.get(i)==null) {
                System.out.println("Elemento Nulo!"); 
            }
        }
    }

    //Opcion 4: Metodo que devuelve y elimina el primer elemento del Array
    public static void extraer(controlArray arreglo) { 
        for(int i = 0; i == 0; i++) {
                System.out.println("La posicion del primer elemento (Posicion " + i + " ) es " + arreglo.get(0));
            }
            boolean cambio = eliminacion();
            if (cambio) {
                Object[] arr = null;
                int possion = (int) arreglo.get(0); // Posicion 0
                for (int i = possion; i < arreglo.longt()- 1; i++) {
                    arr[i] = arr[i+1];
                }
               System.out.println("Se ha eliminado el primer elemento correctamente Siuuuuuuuu"); 
            } else {
                System.out.println("Error al eliminar el elemento!!!!"); 
        }
    }
    
    //Opcion 5: Metodo que solo devuelve el valor del primer elemento del Array
    public static void primero(controlArray arreglo) {
        for(int i = 0; i == 0; i++) {
            System.out.println("La posicion del primer elemento (Posicion " + i + ") es " + arreglo.get(0));
        }
    }
    
    //Opcion 6: Metodo que devuelve en forma de String la información del arrayList
    public static void formaString(controlArray arreglo){
        System.out.println("El arrayList en String es ... "+arreglo.toString());
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
