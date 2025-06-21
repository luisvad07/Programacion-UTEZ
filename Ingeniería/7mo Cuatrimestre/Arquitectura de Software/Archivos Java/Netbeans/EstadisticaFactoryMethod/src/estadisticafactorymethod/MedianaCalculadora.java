/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estadisticafactorymethod;

import java.util.Arrays;

/**
 *
 * @author CC7
 */
public class MedianaCalculadora implements Estadistica {

    private final double[] datos;

    public MedianaCalculadora(double[] datos) {
        this.datos = datos;
    }

    @Override
    public double calcular() {
        Arrays.sort(datos);
        int n = datos.length;

        if (n % 2 == 0) {
            // Si la cantidad de datos es par, se toma el promedio de los dos valores centrales
            int indice1 = n / 2 - 1;
            int indice2 = n / 2;
            return (datos[indice1] + datos[indice2]) / 2.0;
        } else {
            // Si la cantidad de datos es impar, se toma el valor central
            int indiceCentral = n / 2;
            return datos[indiceCentral];
        }
    }
    
    
    
}
