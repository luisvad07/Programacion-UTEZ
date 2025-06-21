/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */

public class Integradora {

    /**
     * @param args the command line arguments
     */
    static Scanner mundial=new Scanner(System.in);
    
    public static void main(String[] args) {
        
        /*1 Clases Abstractas, 1 Interfaz, 1 lista circular simple, 1 lista circular doble, 1 generico, mas herencias, 3 Recursividad*/
        Menus menu = new Menus();//Interfaz 1
        int opc;
        try{
            do {
            System.out.println("---- APLICACION COPA DEL MUNDO QATAR 2022 ----");
            System.out.println("//Menu de Opciones//");
            System.out.println("Selecciona una opción que deseas efectuar: ");
            System.out.println("1. Fase de Grupos"); //Clases Abstractas
            System.out.println("2. Gestionar Selecciones Clasificadas");//Lista Simple
            System.out.println("3. Partidos de la Fase de Grupos");//Pila
            System.out.println("4. Partidos de la Fase Final");//Cola
            System.out.println("5. Gestionar Sedes/Estadios");//Listas Dobles
            System.out.println("6. Gestionar Estadisticas de Jugadores y Selecciones");// Lista Circular Simple y Lista Circular Doble
            System.out.println("7. Salir");
            System.out.print("Opcion: ");
            opc = mundial.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    menu.caso1();
                    break;
                case 2:
                    menu.caso2();
                    break;
                case 3:
                    menu.caso3();
                    break;
                case 4:
                    menu.caso4();
                    break;    
                case 5:
                    menu.caso5();
                    break;
                case 6:
                    menu.caso6();
                    break;    
                case 7:
                    menu.caso7();
                    break;     
                default:
                    System.out.println("Opcion Invalida");
                }
                System.out.println("\n");
            } while (opc != 7);
        }catch(InputMismatchException e){
            System.out.println("\n");
            System.out.println("Debes agregar un numero! "+e);
        }
        System.out.println("Aplicacion Terminada");
    }
    
}
