/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testnumerosenteros;

/**
 *
 * @author clientepreferido
 */
class NumerosArreglos {

    private int[] numeros;
    private int sig, bandera, bandera2 = -1;

    public NumerosArreglos(int longitud) {
        numeros = new int[longitud];
    }

    boolean setNumero(int numero) {
        if (hayEspacio()) {
            numeros[sig] = numero;
            sig++;
            return true;
        }
        return false;
    }

    public void leerNumero(int indice, int numero) {
        numeros[indice] = numero;
    }
    
    public int getNumero(int indice) {
        return numeros[indice];
    }

    public int longitud() {
        return numeros.length;
    }

    public boolean hayEspacio() {
        return sig < numeros.length;
    }
    
    public int buscar(int numero) {
        int aux = 0;
        if (bandera == sig) {
            bandera = 0;
            bandera2 = -1;
        }
        for (int i = 0; i < sig; i++) {
            if (numero == numeros[bandera]) {
                aux = bandera;
                bandera++;
                bandera2 = -2;
                return aux;
            }
            return bandera2;
        }
        return 0;
    }

}
