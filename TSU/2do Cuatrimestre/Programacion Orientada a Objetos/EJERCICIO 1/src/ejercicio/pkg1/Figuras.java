/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio.pkg1;

/**
 *
 * @author CC10
 */
public class Figuras {  
    final double PI = 3.1416;    
    double l=0.0D, r=0.0D, perimetro=0.0D;
    /*Método que permite sumar dos números double, este recibe como parámetros a y b.*/
    public double Triangulo(double l) { 
        perimetro = 3 * l;
        return perimetro;
    }

    public double Cuadrado(double l) {
        perimetro = 4 * l;
        return perimetro;
    }

    public double Circulo(double r) {
        perimetro = PI * (r * r);
        return perimetro;
    }
}

