/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pilas;

/**
 *
 * @author Luis Valladolid
 */
public class NodoPila {
    //1.Atributo que almacena el dato
    Object dato;
    //2.Atributo que apunta al siguente
    NodoPila sig;
    //Constructor
    public NodoPila(Object dato) {
        this.dato = dato;
        this.sig=null;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoPila getSig() {
        return sig;
    }

    public void setSig(NodoPila sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return "Nodo[" + dato /*+ ", sig=" + sig + '}'*/+"]";
    }
}
