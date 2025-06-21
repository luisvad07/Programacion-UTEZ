/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronstrategy;

/**
 *
 * @author CC7
 */
public class PagoPayPal implements PagoStrategy {
    private final String email;

    public PagoPayPal(String email) {
        this.email = email;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pago con PayPal. Monto: " + cantidad);
    }
}

