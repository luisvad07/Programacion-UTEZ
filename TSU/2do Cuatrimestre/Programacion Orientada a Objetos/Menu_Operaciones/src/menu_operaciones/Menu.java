/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu_operaciones;

/**
 *
 * @author clientepreferido
 */
public class Menu {

    double result = 0.0D;

    /*Método que permite sumar dos números double, este recibe como parámetros a y b.*/
    public double sumar(double a, double b) {
        result = a + b;
        return result;
    }

    public double restar(double a, double b) {
        result = a - b;
        return result;
    }

    public double multiplicar(double a, double b) {
        result = a * b;
        return result;
    }

    public double dividir(double a, double b) {
        result = a / b;
        return result;
    }

}
