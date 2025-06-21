/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenrecupera4a;

/**
 *
 * @author CC7
 */
public class PilaEnlazada {
    NodoPila Tope;
    NodoPila Fondo;

    public PilaEnlazada() {
        this.Tope = null;
        this.Fondo = null;
    }

    public NodoPila getTope() {
        return Tope;
    }

    public void setTope(NodoPila Tope) {
        this.Tope = Tope;
    }

    public NodoPila getFondo() {
        return Fondo;
    }

    public void setFondo(NodoPila Fondo) {
        this.Fondo = Fondo;
    }
    
    public void insertar(char x) {
    	NodoPila nuevo = new NodoPila(x);
        if (this.Tope==null){
            nuevo.sig = null;
            this.Tope = nuevo;
            this.Fondo = nuevo;
        } else {
            nuevo.sig = this.Tope;
            this.Tope = nuevo;
        }
    }
    
    public void imprimir() {
        NodoPila iterador = this.Fondo;
        do{
            System.out.println(iterador);
            iterador=iterador.getSig();
        }while(iterador!=null);
    }
    
    public char extraer () {
        if (Tope!=null) {
            char informacion = Tope.dato;
            Tope = Tope.sig;
            return informacion;
        } else {
            return Character.MAX_VALUE;
        }
    }  
    
    public boolean vacia() {
        if (this.Tope==null) {
            return true;
        } else {
            return false;
        }
    }
    
}
