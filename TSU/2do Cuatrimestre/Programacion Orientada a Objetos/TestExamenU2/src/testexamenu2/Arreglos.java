/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testexamenu2;

/**
 *
 * @author CC10
 */
public class Arreglos {

    private final String[] matrizMatricula;
    private final String[] matrizNombre;
    private final int[] matrizCarrera;
    private int cont, encontrado;//encontrado

    public Arreglos(int longitud) {
        matrizMatricula = new String[longitud];
        matrizNombre = new String[longitud];
        matrizCarrera = new int[longitud];
    }

    public void setMatrizMatricula(String matricula) {
        matrizMatricula[cont] = matricula;
    }

    public void setMatrizNombre(String nombre) {
        matrizNombre[cont] = nombre;
    }

    public void setMatrizCarrera(int carrera) {
        this.matrizCarrera[cont] = carrera;
    }

    //////////////////////////sobrecarga
    public void setMatricula(int indice, String matricula) {
        matrizMatricula[indice] = matricula;
    }

    public void setNombre(int indice, String nombre) {
        matrizNombre[indice] = nombre;
    }

    public void setCarrera(int indice, int carrera) {
        this.matrizCarrera[indice] = carrera;
    }

    public String getMatricula(int indice) {
        return matrizMatricula[indice];
    }

    public String getNombre(int indice) {
        return matrizNombre[indice];
    }

    public int getCarrera(int indice, int carrera) {
        return matrizCarrera[indice];
    }

/////////////////////sobrecarga
    public String getMatricula() {
        return matrizMatricula[encontrado];
    }

    public String getNombre() {
        return matrizNombre[encontrado];
    }

    public int getCarrera() {
        return matrizCarrera[encontrado];
    }

    public boolean cupos() {
        return cont < matrizMatricula.length;
    }

    public int busqueda(String leerMatricula) {
        for (int i = 0; i < matrizMatricula.length; i++) {
            if (leerMatricula.equals(matrizMatricula[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public int busquedaMatt(String matricula) {
        for (int i = 0; i < matrizMatricula.length; i++) {
            if (matricula.equals(matrizMatricula[i])) {
                return i;
            }
        }
        return -1;
    }

    public void incrementar() {
        cont++;
    }
}
