/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testejercicio2;

/**
 *
 * @author clientepreferido
 */
public class Arreglos {
    private final String[] matrizClave;
    private final String[] matrizNombre;
    private final int[] matrizEdad;
    private final double[] matrizEstatura;
    private int cont, encontrado;

    public Arreglos(int longitud) {
        matrizClave = new String[longitud];
        matrizNombre = new String[longitud];
        matrizEdad = new int[longitud];
        matrizEstatura = new double[longitud];
    }

    public void setMatrizClave(String clave) {
        matrizClave[cont] = clave;
    }

    public void setMatrizNombre(String nombre) {
        matrizNombre[cont] = nombre;
    }

    public void setMatrizEdad(int edad) {
        this.matrizEdad[cont] = edad;
    }
    
    public void setMatrizEstatura(double estatura) {
        this.matrizEstatura[cont] = estatura;
    }

    //////////////////////////sobrecarga
    public void setMatrizClave(int indice, String clave) {
        matrizClave[indice] = clave;
    }

    public void setMatrizNombre(int indice, String nombre) {
        matrizNombre[indice] = nombre;
    }

    public void setMatrizEdad(int indice, int edad) {
        matrizEdad[indice] = edad;
    }
    
    public void setMatrizEstatura(int indice, double estatura) {
        matrizEstatura[indice] = estatura;
    }

    public String getMatrizClave(int indice) {
        return matrizClave[indice];
    }

    public String getMatrizNombre(int indice) {
        return matrizNombre[indice];
    }

    public int getMatrizEdad(int indice) {
        return matrizEdad[indice];
    }
    
    public double getMatrizEstatura(int indice) {
        return matrizEstatura[indice];
    }

/////////////////////sobrecarga
    public String getMatrizClave() {
        return matrizClave[encontrado];
    }

    public String getMatrizNombre() {
        return matrizNombre[encontrado];
    }

    public int getMatrizEdad() {
        return matrizEdad[encontrado];
    }
    
    public double getMatrizEstatura() {
        return matrizEstatura[encontrado];
    }

    public int busqueda(String clave) {
        for (int i = 0; i < matrizClave.length; i++) {
            if (clave.equals(matrizClave[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public int baja(String leerClave) {
        for (int i = 0; i < matrizClave.length; i++) {
            if (leerClave.equals(matrizClave[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean eliminar(String clave) {
        boolean resultado = false;
        for (int i = 0; i < matrizClave.length; i++) {
            if (clave.equals(matrizClave[i])) {
                for (int j = i; j < matrizClave.length - 1; j++) {
                    matrizClave[j] = matrizClave[j+1];
                }
                matrizClave[matrizClave.length - 1] = null;
                matrizNombre[matrizNombre.length - 1] = null;
                matrizEdad[matrizEdad.length - 1] = 0;
                matrizEstatura[matrizEstatura.length - 1] = 0.0;
                resultado = true;
            }
        }
        return resultado;
    }
    
    public int longitud() {
        return matrizClave.length;
    }

}
