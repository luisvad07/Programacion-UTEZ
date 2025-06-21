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

    @Override
    public String toString() {
        return "PilaEnlazada{" + "Tope=" + Tope + ", Fondo=" + Fondo + '}';
    }

    //Push
    public void insertar(Object dato) {
        //1.Crear un nuevo nodo e inicializar 
        NodoPila nuevoPila = new NodoPila(dato);
        //2. Verifica que el tope este vacio
        if (this.Tope == null) {
            this.Tope = nuevoPila;
            this.Fondo = nuevoPila;
        } else {
            //Si tope no es nulo
            nuevoPila.sig = this.Tope;
            this.Tope = nuevoPila;
        }
        //Imprimir como esta la pila
        System.out.println("Elemento insertado:");
        imprimir();
    }

    //POP
    public void Eliminar() {
        if(this.Tope.getSig()!=null){
            this.Tope = this.Tope.getSig();
            imprimir();
        }else{
            System.out.println("Pila Vacia");
        } 
 
   }

    public void imprimir() {
        NodoPila iterador = this.Tope;
        do{
            System.out.println(iterador);
            iterador=iterador.getSig();
        }while(iterador!=null);
    }
}
