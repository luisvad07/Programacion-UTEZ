/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronstrategy;

/**
 *
 * @author CC7
 */
public class CarroCompra {
    private PagoStrategy pagoStrategy;

    public void setPagoStrategy(PagoStrategy pagoStrategy) {
        this.pagoStrategy = pagoStrategy;
    }

    public void checkout(int cantidad) {
        if (pagoStrategy != null) {
            pagoStrategy.pagar(cantidad);
        } else {
            System.out.println("Selecciona una opción de pago antes de realizar el checkout.");
        }
    }
}
