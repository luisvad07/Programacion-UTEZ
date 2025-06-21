/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenrecupera4a;

/**
 *
 * @author CC7
 */
public class NodoPila {
    char dato;
    NodoPila sig;

    public NodoPila(char dato) {
        this.dato = dato;
        this.sig=null;
    }

    public char getDato() {
        return dato;
    }

    public void setDato(char dato) {
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
        return "Nodo[ Caracter: " + dato /*+ ", sig=" + sig + '}'*/+"]";
    }
}
