/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estadisticafactorymethod;

/**
 *
 * @author CC7
 */
public class ModaCalculadora implements Estadistica {

    private final double[] datos;

    public ModaCalculadora(double[] datos) {
        this.datos = datos;
    }
    
    @Override
    public double calcular() {
        double moda = datos[0];
        int maxFrecuencia = 1;

        for (int i = 0; i < datos.length; i++) {
            int frecuencia = 1;

            for (int j = i + 1; j < datos.length; j++) {
                if (datos[i] == datos[j]) {
                    frecuencia++;
                }
            }

            if (frecuencia > maxFrecuencia) {
                maxFrecuencia = frecuencia;
                moda = datos[i];
            }
        }

        return moda;
    }

    
    
}
