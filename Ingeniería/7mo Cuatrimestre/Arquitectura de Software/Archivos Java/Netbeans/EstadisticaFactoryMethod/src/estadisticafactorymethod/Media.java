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
public class Media extends EstadisticaDatos {

    @Override
    double calcular(double[] datos) {
          double resultado;
          Estadistica uno = new MediaCalculadora(datos);
          resultado = uno.calcular();
          return resultado;
    }
}
