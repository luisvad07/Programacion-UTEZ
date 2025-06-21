package listacirculardoble;

public class ListaEnlazadaDoble {

    private Nodo inicio, fin;
    private int tamaño;

    public ListaEnlazadaDoble() {
        inicio = fin = null;
        tamaño = 0;
    }
    
    public int getTamaño() {
        return tamaño;
    }

    public boolean esVacio() {
        return inicio == null;
    }
    
    public void agregar(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (this.inicio == null) {
            this.inicio = nuevo;
            this.inicio.siguiente = inicio;
            this.inicio.anterior = fin;
            fin = nuevo;
        } else {
            this.fin.siguiente = nuevo;
            nuevo.siguiente = inicio;
            this.fin = nuevo;
            this.inicio.anterior = fin;
        }
    }
    
    public void imprimirLista() {
        System.out.println("---- NODOS ----");
        Nodo iterador = inicio;
        int it = 1;
        try {
            while (iterador.siguiente != this.inicio) {
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
            Nodo aux = fin.anterior;
            if (aux == null) {
                inicio = null;
                fin = null;
            } else {
                aux.siguiente = null;
                fin = aux;
            }
            tamaño--;
        }
    }

    public void eliminarInicio() {
        if (!esVacio()) {
            Nodo head = inicio.siguiente;
            if (head == null) {
                inicio = null;
                fin = null;
            } else {
                head.anterior = null;
                inicio = head;
            }
            tamaño--;
        }

    }

    public void eliminarPos(int pos) {
        if (esVacio()) {
            System.out.println("Lista Vacia!");
        } else {
            Nodo aux = inicio;
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
                Nodo aux = inicio;
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
        Nodo head = inicio;
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
