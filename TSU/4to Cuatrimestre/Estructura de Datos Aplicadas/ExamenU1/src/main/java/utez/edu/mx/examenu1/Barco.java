/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utez.edu.mx.examenu1;

/**
 *
 * @author CC7
 */
public class Barco extends Abstract {

    public Barco() {
    }

    @Override
    public void ingresarCombustible() {
        System.out.print("Ingrese los litros de combustible del Transporte Barco: ");
        String lit = florecita.next();
        this.setLitros_combustible(Double.parseDouble(lit));
    }

    @Override
    public void calcularDistanciaGasNatural() {
        System.out.println("\n");
        System.out.println("---- DATOS TRANSPORTE ------");
        System.out.println("Transporte Tipo: " + tipo_transporte.MARITIMO);
        this.setKilometros_disponibles(this.getLitros_combustible()*(combustible.GASNATURAL.getCombus()-tipo_transporte.MARITIMO.getValue()));
        System.out.println("La cantidad de Kilometros a recorrer es de: " + this.getKilometros_disponibles());
    }

    @Override
    public void calcularDistanciaGasolina() {
        System.out.println("\n");
        System.out.println("---- DATOS TRANSPORTE ------");
        System.out.println("Transporte Tipo: " + tipo_transporte.MARITIMO);
        this.setKilometros_disponibles(this.getLitros_combustible()*(combustible.GASOLINA.getCombus()-tipo_transporte.MARITIMO.getValue()));
        System.out.println("La cantidad de Kilometros a recorrer es de: " + this.getKilometros_disponibles());
    }

    @Override
    public void calcularDistanciaDiesel() {
        System.out.println("\n");
        System.out.println("---- DATOS TRANSPORTE ------");
        System.out.println("Transporte Tipo: " + tipo_transporte.MARITIMO);
        this.setKilometros_disponibles(this.getLitros_combustible()*(combustible.DIESEL.getCombus()-tipo_transporte.MARITIMO.getValue()));
        System.out.println("La cantidad de Kilometros a recorrer es de: " + this.getKilometros_disponibles() + " KM");
    }
    
}
