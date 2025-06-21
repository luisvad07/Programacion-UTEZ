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
public class MediaCalculadora implements Estadistica {
    
    private final double[] datos;

    public MediaCalculadora(double[] datos) {
        this.datos = datos;
    }

    @Override
    public double calcular() {
        double suma = Arrays.stream(datos).sum() / datos.length;
        return suma;
    }
    
}
