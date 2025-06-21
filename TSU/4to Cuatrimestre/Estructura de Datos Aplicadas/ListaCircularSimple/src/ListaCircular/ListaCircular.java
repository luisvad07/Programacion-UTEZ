package ListaCircular;

public class ListaCircular {

    private Nodo cabeza;
    private int tamaño;

    public ListaCircular() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public boolean esVacio() {
        return cabeza == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo cabeza) {
        this.cabeza = cabeza;
    }

    public void insertar(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (this.cabeza == null) {
            this.cabeza = nuevo;
            this.cabeza.setSiguiente(this.cabeza);
        } else {
            Nodo iterador = this.cabeza;
            while (iterador.getSiguiente() != this.cabeza) {
                iterador = iterador.getSiguiente();
            }
            iterador.setSiguiente(nuevo);
            nuevo.setSiguiente(this.cabeza);
        }
    }

    public void imprimir() {
        Nodo iterador = this.cabeza;
        int it = 1;
        try {
            while (iterador.siguiente != this.cabeza) {
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
            Nodo aux = cabeza.anterior;
            if (aux == null) {
                cabeza = null;
                cabeza = null;
            } else {
                aux.siguiente = null;
                cabeza = aux;
            }
            tamaño--;
        }
    }

    public void eliminarInicio() {
        if (!esVacio()) {
            Nodo head = cabeza.siguiente;
            if (head == null) {
                cabeza = null;
                cabeza = null;
            } else {
                head.anterior = null;
                cabeza = head;
            }
            tamaño--;
        }

    }

    public void eliminarPos(int pos) {
        if (esVacio()) {
            System.out.println("Lista Vacia!");
        } else {
            Nodo aux = cabeza;
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

    public void actualizarDato(int pos, int dato) {
        if (!esVacio()) {
            try {
                Nodo aux = cabeza;
                for (int i = 0; i < pos - 1; i++) {
                    aux = aux.getSiguiente();
                }
                aux.setDato(dato);
            } catch (Exception e) {
                System.out.println("Limite de la lista superado");
            }
        }

    }

    public boolean contiene(int dato) {
        Nodo head = cabeza;
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
