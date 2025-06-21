package listadoble;

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
    
    public void agregarInicio(int dato) {
        if (!esVacio()) {
            inicio = new Nodo(dato, inicio, null);
            inicio.siguiente.anterior = inicio;
        } else {
            inicio = fin = new Nodo(dato);
        }
        tamaño++;
    }

    public void agregarFinal(int dato) {
        if (!esVacio()) {
            fin = new Nodo(dato, null, fin);
            fin.anterior.siguiente = fin;
        } else {
            inicio = fin = new Nodo(dato);
        }
        tamaño++;
    }

    public void imprimirListaInicioFin() {
        int it = 0;
        System.out.println("---- NODOS ----");
        if (!esVacio()) {
            String datos = "=>";
            Nodo aux = inicio;
            while (aux != null) {
                datos = datos + "Nodo "+it+"[" + aux.dato + "]" + "<=>";
                aux = aux.siguiente;
                it++;
            }
            System.out.println(datos);
        }
    }

    public void imprimirListaFinInicio() {
        int it = 0;
        System.out.println("---- NODOS ----");
        if (!esVacio()) {
            String datos = "<=>";
            Nodo aux = fin;
            while (aux != null) {
                datos = datos + "Nodo "+it+"[" + aux.dato + "]" + "<=>";
                aux = aux.anterior;
                it++;
            }
            System.out.println(datos);
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
