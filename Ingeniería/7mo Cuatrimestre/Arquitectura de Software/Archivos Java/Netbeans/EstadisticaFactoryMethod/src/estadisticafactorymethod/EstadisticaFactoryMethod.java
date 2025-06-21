/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package estadisticafactorymethod;

import java.util.Arrays;

/**
 *
 * @author CC7
 */
public class EstadisticaFactoryMethod {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] datos = {1, 2, 2, 3, 4, 5, 6, 7, 8, 9};
        
        System.out.println("Datos del Array -> " + Arrays.toString(datos));

        // Crear objetos estadísticos usando la factoría
        EstadisticaDatos media = new Media();
        EstadisticaDatos mediana = new Mediana();
        EstadisticaDatos moda = new Mediana();

        // Calcular y mostrar resultados
        System.out.println("Media: " + media.calcular(datos));
        System.out.println("Moda: " + moda.calcular(datos));
        System.out.println("Mediana: " + mediana.calcular(datos));
    }
    
}
