package listadoble;

public class Nodo {
    int dato;
    Nodo siguiente, anterior;

    public Nodo(int dato, Nodo siguiente, Nodo anterior) {

        this.dato = dato;
        this.siguiente = siguiente;
        this.anterior = anterior;

    }

    public Nodo(int dato) {
        this(dato,null,null);
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    @Override
    public String toString() {
        return "Dato=" + dato+",";
    }

}
