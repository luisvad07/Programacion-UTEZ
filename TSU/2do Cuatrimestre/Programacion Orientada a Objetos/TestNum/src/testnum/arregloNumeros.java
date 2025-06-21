/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnum;

/**
 *
 * @author clientepreferido
 */
public class arregloNumeros {

    private int numeros[];
    private int siguiente, bandera, indice, index, auxA;

    public arregloNumeros(int longitud) {
        numeros = new int[longitud];
    }

    public void incrementar() {
        index++;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setNumero(int sig, int numero) {
        numeros[sig] = numero;

    }

    public int getNumero(int sig) {
        return numeros[sig];
    }

    public int longitud() {
        return numeros.length;
    }

    public int buscar(int numero) {
        int auxt = -1;
        for (int i = 0; i < index; i++) {
            if (numero == this.numeros[i]) {
                auxt = i;
            }
        }
        return auxt;
    }

    public boolean eliminar(int numero) {
        boolean resultado = false;
        for (int i = 0; i < numeros.length; i++) {
            if (numero == numeros[i]) {
                for (int j = i; j < numeros.length - 1; j++) {
                    numeros[j] = numeros[j + 1];
                }
                numeros[numeros.length - 1] = 0;

                resultado = true;
            }
        }
        return resultado;
    }

    public boolean isHayEspacio() {
        boolean space = false;
        if (index < numeros.length) {
            space = true;
        }
        return space;
    }
}
