/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testordenarnombres;

/**
 *
 * @author clientepreferido
 */
class OrdenarNombres {
    private String[] nombres;
    
    public OrdenarNombres(int longitud) {
        nombres = new String[longitud];
    }
    
    public void setNombre(int indice, String nombre) {
        nombres[indice] = nombre;
    }

    public String getNombre(int indice) {
        return nombres[indice];
    }
    
    public int longitud() {
        return nombres.length;
    }
    
    public void Ordenar() {
        String temp;
        for (int i = 0; i < nombres.length -1; i++) {
            for (int j = 0; j < nombres.length -1; j++) {
                if (nombres[j].compareTo(nombres[j+1])>0){ // < >
                    temp = nombres[j+1];
                    nombres[j+1] = nombres[j];
                    nombres[j] = temp;
                }
            }
        }
    }
}
