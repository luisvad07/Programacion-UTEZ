/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenu3;

/**
 *
 * @author CC7
 */
public class Listas {
    private Nodo venta;
    private int tamaño;

    public Listas() {
        this.venta = null;
        this.tamaño = 0;
    }
    
    public boolean esVacio() {
        return venta == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }

    public Nodo getCabeza() {
        return venta;
    }

    public void setCabeza(Nodo cabeza) {
        this.venta = cabeza;
    }

    public void insertar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (this.venta == null) {
            this.venta = nuevo;
            this.venta.setSiguiente(this.venta);
        } else {
            Nodo iterador = this.venta;
            while (iterador.getSiguiente() != this.venta) {
                iterador = iterador.getSiguiente();
            }
            iterador.setSiguiente(nuevo);
            nuevo.setSiguiente(this.venta);
        }
    }

    public void imprimir() {
        Nodo iterador = this.venta;
        int it = 1;
        try {
            while (iterador.siguiente != this.venta) {
                System.out.println("Nodo " + it + ":");
                System.out.println(iterador + "{der:" + iterador.siguiente + "}");
                iterador = iterador.siguiente;
                it++;
            }
            System.out.println("Nodo " + it + ":");
            System.out.println(iterador + "{der:" + iterador.siguiente +"}");
            iterador = iterador.siguiente;
        } catch (NullPointerException e){
            System.out.println("Nodos Vacios!");
            System.out.println(e);
        }
    }
    
    public void eliminarFinal() {
        if (!esVacio()) {
            Nodo aux = venta.anterior;
            if (aux == null) {
                venta = null;
                venta = null;
            } else {
                aux.siguiente = null;
                venta = aux;
            }
            tamaño--;
        }
    }

    public void eliminarInicio() {
        if (!esVacio()) {
            Nodo head = venta.siguiente;
            if (head == null) {
                venta = null;
                venta = null;
            } else {
                head.anterior = null;
                venta = head;
            }
            tamaño--;
        }

    }

    public void eliminarPos(int pos) {
        if (esVacio()) {
            System.out.println("Lista Vacia!");
        } else {
            Nodo aux = venta;
            if (pos > tamaño) {
                eliminarFinal();
            } else if (pos == 0) {
                eliminarInicio();
            } else {
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.siguiente;
                }
                aux.siguiente.anterior = aux.anterior;
                aux.anterior.siguiente = aux.siguiente;
            }
            tamaño--;
        }
    }

    public void actualizarDato(int pos, Object dato) {
        if (!esVacio()) {
            try {
                Nodo aux = venta;
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.getSiguiente();
                }
                aux.setDato(dato);
            } catch (Exception e) {
                System.out.println("Limite de la lista superado");
            }
        }

    }

    public boolean contiene(Object dato) {
        Nodo head = venta;
        for (int i = 0; head != null; i++) {
            if (head.dato == dato) {
                return true;
            } else {
                head = head.siguiente;
            }
        }
        return false;
    }
}
