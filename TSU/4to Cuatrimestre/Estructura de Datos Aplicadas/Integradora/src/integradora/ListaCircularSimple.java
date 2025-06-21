/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import integradora.clasesabstractas.Estadisticas;
import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class ListaCircularSimple {
    String nomJugador;
    String Seleccion;
    int Goles;
    ListaCircularSimple siguiente, anterior; //Nodo

    public ListaCircularSimple(String nomJugador, String Seleccion, int Goles) {
        this.nomJugador = nomJugador;
        this.Seleccion = Seleccion;
        this.Goles = Goles;
        this.siguiente = null;
        this.anterior = null;
    }

    @Override
    public String toString() {
        return "ListaCircularSimple{" + "nomJugador=" + nomJugador + ", Seleccion=" + Seleccion + ", Goles=" + Goles + '}';
    }

}

class EstadisticasJugadoresCircularSimple extends Estadisticas { //Generico y Herencia

    static Scanner mundial=new Scanner(System.in);
    private ListaCircularSimple cabeza;
    int tamaño = 0;
    String lista="";

    public EstadisticasJugadoresCircularSimple() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public boolean esVacio() {
        return cabeza == null;
    }
    
    public Object getTamaño() {
        return tamaño;
    }

    public ListaCircularSimple getCabeza() {
        return cabeza;
    }

    public void setCabeza(ListaCircularSimple cabeza) {
        this.cabeza = cabeza;
    }

    @Override
    public void insertar(String nomJugador, String Seleccion, int Goles) {
        ListaCircularSimple nuevo = new ListaCircularSimple(nomJugador, Seleccion,Goles);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
    }
    
    @Override
    public void imprimir() {
        ListaCircularSimple iterador = this.cabeza;
        int it = 1;
        while (iterador.siguiente != null){
            lista += "Nodo " + it + ":" + "{" + iterador + "{der:" + iterador.siguiente + "}";
            iterador = iterador.siguiente;
            it++;
        }
        System.out.println(lista);
        System.out.println("Nodo " + it + ":");
        System.out.println(iterador + "{der:" + iterador.siguiente +"}");
        iterador = iterador.siguiente;
        lista = "";
    }
    
    @Override
    public String eliminarPos() {
        String auxiliar = cabeza.nomJugador;
        cabeza = cabeza.siguiente;
        tamaño--;
        System.out.println("Partido Eliminado Correctamente");
        return auxiliar;
    }

    @Override
    public void actualizarDato() {
        ListaCircularSimple almacenamiento = cabeza;
        boolean encontrado = false;
        mundial.useDelimiter("\n");
        System.out.println("-- BUSQUEDA DE JUGADOR --");
        System.out.print("Ingrese Jugador: ");
        String jug = mundial.next();
        if (cabeza!=null){
            while (almacenamiento != null && encontrado != true){
                if (almacenamiento.nomJugador.equals(jug)){
                    System.out.println("--- Nodo encontrado ---");
                    System.out.println(almacenamiento.nomJugador + "{" + "Seleccion=" + almacenamiento.Seleccion + ", Goles=" + almacenamiento.Goles + '}');
                    encontrado = true;
                    System.out.println("Ingrese nuevo valor para jugador: ");
                    almacenamiento.nomJugador = mundial.next();
                    System.out.println("Ingrese nuevo valor para seleccion en el que juega: ");
                    almacenamiento.Seleccion = mundial.next();
                    System.out.println("Ingrese nuevo valor para goles: ");
                    almacenamiento.Goles = mundial.nextInt();
                    encontrado = true;
                }
                almacenamiento = almacenamiento.siguiente;
            }
            if (!encontrado){
                System.out.println("Nodo no encontrado");
            }
        }else {
            System.out.println("La Lista esta vacia");
        }
    }

    @Override
    public void contiene() {
        ListaCircularSimple busq = cabeza;
        boolean encontrado = false;
        System.out.println("-- BUSQUEDA DE JUGADOR --");
        System.out.print("Ingrese Jugador: ");
        String jug = mundial.next();
        if (cabeza!=null){
            while (busq != null && encontrado != true){
                if (busq.nomJugador.equals(jug)){
                    System.out.println("--- Nodo encontrado ---");
                    System.out.println(busq.nomJugador + "{" + "Seleccion=" + busq.Seleccion + ", Goles=" + busq.Goles + '}');
                    encontrado = true;
                }
                busq = busq.siguiente;
            }
            if (!encontrado){
                System.out.println("Nodo no encontrado");
            }
        }else {
            System.out.println("La Lista esta vacia");
        }
    }
}
