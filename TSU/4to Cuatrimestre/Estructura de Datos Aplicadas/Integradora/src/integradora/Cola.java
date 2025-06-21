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
public class Cola {
    String tipo_partido;
    String equipo1;
    String resultado;
    String equipo2;
    Cola siguiente; //Nodo
}

class FaseFinalCola{
    Scanner foot = new Scanner(System.in);
    private Cola match1,matchU;
    int cont = 0;
    String colaPartido = "";

    public FaseFinalCola(){
        match1 = null;
        matchU = null;
    }

    public boolean colaVacia(){
        return match1 == null;
    }

    public void agregarCola(String tipo_partido, String equipo1, String resultado,String equipo2){
        Cola newmatch = new Cola();
        newmatch.tipo_partido = tipo_partido;
        newmatch.equipo1 = equipo1;
        newmatch.resultado = resultado;
        newmatch.equipo2 = equipo2;
        newmatch.siguiente = null;

        if (colaVacia()){
            match1 = newmatch;
            matchU = newmatch;
        }else {
            matchU.siguiente = newmatch;
            matchU = newmatch;
        }
    }

    public void modificarCola(){
        Cola almacenamiento;
        boolean encontrado = false;
        almacenamiento = match1;
        foot.useDelimiter("\n");
        System.out.println("-- BUSQUEDA DE PARTIDO --");
        System.out.print("Ingresa equipo Local: ");
        String local = foot.next();
        System.out.print("Ingresa equipo Visitante: ");
        String visitante = foot.next();
        if (match1!=null){
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

    public void mostrarCola(){
        Cola recorrido;
        int i = 0;
        recorrido = match1;
        if (match1!=null){
            while (recorrido != null){
                System.out.println(recorrido.tipo_partido + "{" + "Local=" + recorrido.equipo1 + ", resultado=" + recorrido.resultado + ", Visitante=" + recorrido.equipo2 + '}');
                recorrido = recorrido.siguiente;
                i++;
            }
        }else {
            System.out.println("La Cola esta vacia");
        }
    }
    
    public void consultarCola(){
        Cola busq;
        boolean encontrado = false;
        busq = match1;
        System.out.println("-- BUSQUEDA DE PARTIDO --");
        System.out.print("Ingresa equipo Local: ");
        String local = foot.next();
        System.out.print("Ingresa equipo Visitante: ");
        String visitante = foot.next();
        if (match1!=null){
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
    
    public String eliminarCola(){
        String auxiliar = match1.equipo1;
        String auxiliar2 = match1.equipo2;
        match1 = match1.siguiente;
        cont--;
        System.out.println("Partido Eliminado Correctamente");
        return auxiliar + auxiliar2;
    }
    
    
}
