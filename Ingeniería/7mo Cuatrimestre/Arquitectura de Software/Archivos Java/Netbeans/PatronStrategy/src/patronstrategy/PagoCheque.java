/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronstrategy;

/**
 *
 * @author CC7
 */
public class PagoCheque implements PagoStrategy {
    
    private final String nombrePersona;

    public PagoCheque(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    @Override
    public void pagar(int cantidad) {
        System.out.println("Pago con Cheque. Monto: " + cantidad);
    }
}
