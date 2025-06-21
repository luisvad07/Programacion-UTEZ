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
public class Pila {
    String tipo_partido;
    String equipo1;
    String resultado;
    String equipo2;
    Pila siguiente;

    public Pila(String tipo_partido, String equipo1, String resultado, String equipo2) {
        this.tipo_partido = tipo_partido;
        this.equipo1 = equipo1;
        this.resultado = resultado;
        this.equipo2 = equipo2;
        siguiente = null;
    }
}

class FaseGruposPila{
    Scanner foot = new Scanner(System.in);
    private Pila matchU;
    int cont = 0;
    String lista="";

    public FaseGruposPila(){
        matchU = null;
        cont = 0;
    }

    public boolean isVacia(){
        return matchU == null;
    }

    public void agregarPila(String tipo_partido, String equipo1, String resultado, String equipo2){
        Pila nuevo = new Pila(tipo_partido,equipo1,resultado,equipo2);
        nuevo.siguiente = matchU;
        matchU = nuevo;
        cont++;
    }
    
    public void modificarPila(){
        Pila almacenamiento;
        boolean encontrado = false;
        almacenamiento = matchU;
        foot.useDelimiter("\n");
        System.out.println("-- BUSQUEDA DE PARTIDO --");
        System.out.print("Ingresa equipo Local: ");
        String local = foot.next();
        System.out.print("Ingresa equipo Visitante: ");
        String visitante = foot.next();
        if (matchU!=null){
            while (almacenamiento != null && encontrado != true){
                if (almacenamiento.equipo1.equals(local) || almacenamiento.equipo2.equals(visitante)){
                    System.out.println("--- Nodo encontrado ---");
                    System.out.println(almacenamiento.tipo_partido + "{" + "Local=" + almacenamiento.equipo1 + ", resultado=" + almacenamiento.resultado + ", Visitante=" + almacenamiento.equipo2 + '}');
                    System.out.println("Ingrese nuevo valor para tipo de partido: ");
                    almacenamiento.tipo_partido = foot.next();
                    System.out.println("Ingrese nuevo valor para equipo local: ");
                    almacenamiento.equipo1 = foot.next();
                    System.out.println("Ingrese nuevo valor para equipo visitante: ");
                    almacenamiento.equipo2 = foot.next();
                    System.out.println("Ingrese nuevo valor para resultado: ");
                    almacenamiento.resultado = foot.next();
                    encontrado = true;
                }
                almacenamiento = almacenamiento.siguiente;
            }
            if (!encontrado){
                System.out.println("Nodo no encontrado");
            }
        }else {
            System.out.println("La Cola esta vacia");
        }
    }

    public String eliminarPila(){
        String auxiliar = matchU.equipo1;
        String auxiliar2 = matchU.equipo2;
        matchU = matchU.siguiente;
        cont--;
        System.out.println("Partido Eliminado Correctamente");
        return auxiliar + auxiliar2;
    }

    public void mostrarPila(){
        Pila recorrido = matchU;
        while (recorrido != null){
            lista += recorrido.tipo_partido + "{" + "Local=" + recorrido.equipo1 + ", resultado=" + recorrido.resultado + ", Visitante=" + recorrido.equipo2 + '}';
            recorrido = recorrido.siguiente;
        }
        System.out.println(lista);
        lista = "";
    }
    
    public void consultarPila(){
        Pila busq;
        boolean encontrado = false;
        busq = matchU;
        System.out.println("-- BUSQUEDA DE PARTIDO --");
        System.out.print("Ingresa equipo Local: ");
        String local = foot.next();
        System.out.print("Ingresa equipo Visitante: ");
        String visitante = foot.next();
        if (matchU!=null){
            while (busq != null && encontrado != true){
                if (busq.equipo1.equals(local) || busq.equipo2.equals(visitante)){
                    System.out.println("--- Nodo encontrado ---");
                    System.out.println(busq.tipo_partido + "{" + "Local=" + busq.equipo1 + ", resultado=" + busq.resultado + ", Visitante=" + busq.equipo2 + '}');
                    encontrado = true;
                }
                busq = busq.siguiente;
            }
            if (!encontrado){
                System.out.println("Nodo no encontrado");
            }
        }else {
            System.out.println("La Cola esta vacia");
        }
    }
}
