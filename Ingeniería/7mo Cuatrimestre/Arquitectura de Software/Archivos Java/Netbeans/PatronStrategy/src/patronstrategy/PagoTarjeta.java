/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronstrategy;

/**
 *
 * @author CC7
 */
public class PagoTarjeta implements PagoStrategy {
    private final String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pago con Tarjeta de Crédito/Débito. Monto: " + cantidad);
    }
}
