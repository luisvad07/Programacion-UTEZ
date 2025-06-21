/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronstrategy;

/**
 *
 * @author CC7
 */
public class PagoTransferencia implements PagoStrategy {
    
    private final String numeroCuenta;

    public PagoTransferencia(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pago con Transferencia Bancaria. Monto: " + cantidad);
    }
}
