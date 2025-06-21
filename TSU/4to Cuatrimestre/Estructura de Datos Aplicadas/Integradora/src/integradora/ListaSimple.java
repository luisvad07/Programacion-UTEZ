/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integradora;

import java.util.Scanner;

/**
 *
 * @author Luis Valladolid
 */
public class ListaSimple {
    String dato;
    ListaSimple siguiente; //Nodo
}

class SeleccionesSimple<T>{
    Scanner leer = new Scanner(System.in);
    ListaSimple inicio, ultimo;

    public SeleccionesSimple(){
        inicio = null;
    }

    public void agregarNodo(String x){
        ListaSimple nuevo = new ListaSimple();
        nuevo.dato = x;

        if (inicio == null){
            inicio = nuevo;
            inicio.siguiente=null;
            ultimo = inicio;
        }else{
            ultimo.siguiente = nuevo;
            nuevo.siguiente = null;
            ultimo = nuevo;
        }
    }

    public void mostrarListaSimple(){
        ListaSimple actual = inicio;
        int cont = 1;
        if (actual == null) {
                System.out.println("Lista Vacia!");
        } else {
            while (actual != null){
            System.out.println("Seleccion " + cont + " -> " + actual.dato);
            actual = actual.siguiente;
            cont++;    
        }
        }
    }

    public void mostrarNodo(String x){
        ListaSimple actual = inicio;
        while (actual != null){
            if (actual.dato == null ? x == null : actual.dato.equals(x)){
                System.out.println("Nodo: "+actual.dato);
            }
            actual = actual.siguiente;
        }
    }

    public void modificarNodo(String x){
        ListaSimple actual = inicio;
        while (actual!= null){
            if (actual.dato == null ? x == null : actual.dato.equals(x)){
                System.out.println("Dato encontrado!");
                System.out.print("Ingresa el nuevo dato: ");
                actual.dato = leer.next();
            }
            actual = actual.siguiente;
        }
    }

    public void eliminarNodo(String x){
        ListaSimple actual;
        ListaSimple anterior = new ListaSimple();
        actual = inicio;
        while (actual!=null){
            if (actual.dato == null ? x == null : actual.dato.equals(x)){
                if (actual == inicio){
                    inicio = inicio.siguiente;
                }else {
                    anterior.siguiente = actual.siguiente;
                }
            }
            anterior = actual;
            actual =actual.siguiente;
        }
    }


}