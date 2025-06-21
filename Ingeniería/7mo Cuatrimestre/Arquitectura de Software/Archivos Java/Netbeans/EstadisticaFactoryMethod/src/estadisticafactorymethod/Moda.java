/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estadisticafactorymethod;

/**
 *
 * @author CC7
 */
public class Moda extends EstadisticaDatos {
    
    @Override
    double calcular(double[] datos) {
          double resultado;
          Estadistica uno = new MedianaCalculadora(datos);
          resultado = uno.calcular();
          return resultado;
    }
}
