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
public class ListaDoble {
    String dato;
    ListaDoble siguiente,anterior; //Nodos
}

class SedesDoble<E>{
    Scanner leer = new Scanner(System.in);
    ListaDoble inicio, fin;

    public SedesDoble(){
        inicio = fin = null;
    }


    public void agregarNodo(String x){
        ListaDoble nuevo = new ListaDoble();
        nuevo.dato = x;
        if (inicio == null){
            inicio = nuevo;
            inicio.siguiente = null;
            inicio.anterior = null;
            fin = inicio;
        }else {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            nuevo.siguiente = null;
            fin = nuevo;
        }
    }

    public void mostrarListaDoble(){
        ListaDoble actual;
        int cont = 1;
        actual = fin;
        while (actual!=null){
            System.out.println("Estadio " + cont + " -> " + actual.dato);
            cont++;
            actual = actual.anterior;
        }
    }

    public void buscarNodo(String dato){
        ListaDoble actual = inicio;
        while (actual!=null){
            if (actual.dato == null ? dato == null : actual.dato.equals(dato)){
                System.out.println("Nodo: "+actual.dato);
            }
            actual = actual.siguiente;
        }
    }

    public void modificarNodo(String dato){
        ListaDoble actual = fin;
        while (actual!=null){
            if (actual.dato == null ? dato == null : actual.dato.equals(dato)){
                System.out.println("Dato encontrado!");
                System.out.println("Ingresa el nuevo dato");
                actual.dato = leer.next();
            }
            actual = actual.siguiente;
        }
    }
}