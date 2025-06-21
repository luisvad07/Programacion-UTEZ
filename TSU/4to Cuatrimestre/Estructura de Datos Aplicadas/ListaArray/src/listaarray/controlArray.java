/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listaarray;

import java.util.Arrays;

/**
 *
 * @author Luis
 */
public class controlArray<E> {

    private Object[] longitud;
    public int extra;

    //Constructor
    public controlArray(int tam) {
        // Crea una nueva array de objetos de la longitud especificada
        this.longitud = new Object[tam];
        this.extra = tam;
    }

    // Método para devolver el indice
    public E get(int i) {
        final E e = (E) longitud[i];
        return e;
    }

    // Método para establecer un valor `e` en el índice `i` en la array
    void set(int i, E e) {
        longitud[i] = e;
    }
    
    public int longt() {
        return this.longitud.length;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(longitud);
    }
}
