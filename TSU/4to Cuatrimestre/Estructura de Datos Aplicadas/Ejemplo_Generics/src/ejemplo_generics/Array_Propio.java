/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo_generics;

import java.util.Arrays;

/**
 *
 * @author carsi
 */
public class Array_Propio<E> {

    private Object[] arr;
    public int tam;

    //Constructor
    public Array_Propio(int tam) {
        // Crea una nueva array de objetos de la longitud especificada
        this.arr = new Object[tam];
        this.tam = tam;
    }

    // Método para hacer que el objeto esté presente en el índice `i` en la array
    public E get(int i) {
        final E e = (E) arr[i];
        return e;
    }

    // Método para establecer un valor `e` en el índice `i` en la array
    void set(int i, E e) {
        arr[i] = e;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}
