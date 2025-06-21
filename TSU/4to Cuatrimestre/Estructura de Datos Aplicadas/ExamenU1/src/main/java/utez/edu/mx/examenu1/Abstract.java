/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utez.edu.mx.examenu1;

import java.util.Scanner;

/**
 *
 * @author CC7
 */
public abstract class Abstract {
    static Scanner florecita = new Scanner(System.in);
    
    enum tipo_transporte {
        AEREO(6), MARITIMO(8), TERRESTRE(5);
        
        private final int value;

        private tipo_transporte(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    enum combustible {
        GASNATURAL(53.6),GASOLINA(46.0), DIESEL(42.7);
        
        private final double combus;

        private combustible(double combus) {
            this.combus = combus;
        }

        public double getCombus() {
            return combus;
        }
    }
    
    private double litros_combustible;
    private double kilometros_disponibles;

    public double getLitros_combustible() {
        return litros_combustible;
    }

    public void setLitros_combustible(double litros_combustible) {
        this.litros_combustible = litros_combustible;
    }

    public double getKilometros_disponibles() {
        return kilometros_disponibles;
    }

    public void setKilometros_disponibles(double kilometros_disponibles) {
        this.kilometros_disponibles = kilometros_disponibles;
    }
    
    public abstract void ingresarCombustible();
    public abstract void calcularDistanciaGasNatural();
    public abstract void calcularDistanciaGasolina();
    public abstract void calcularDistanciaDiesel();
}
