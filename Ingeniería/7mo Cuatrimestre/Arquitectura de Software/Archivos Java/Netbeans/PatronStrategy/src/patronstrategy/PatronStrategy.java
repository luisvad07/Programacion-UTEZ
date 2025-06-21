/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package patronstrategy;

import java.util.Scanner;

/**
 *
 * @author CC7
 */
public class PatronStrategy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner leer = new Scanner(System.in);

        // Crear instancias de las estrategias de pago
        PagoTarjeta pagoTarjeta = new PagoTarjeta("1234-5678-9101-1121");
        PagoPayPal pagoPayPal = new PagoPayPal("luisvad007@hotmail.com");
        PagoTransferencia pagoTransferencia = new PagoTransferencia("987654321");
        PagoCheque pagoCheque = new PagoCheque("Luis Valladolid");

        // Crear el carrito de compras
        CarroCompra carroCompra = new CarroCompra();

        // Solicitar al usuario que elija la estrategia de pago
        System.out.println("--------- BIENVENIDO A SHEIN--------------");
        System.out.println("1. Tarjeta de Crédito/Débito");
        System.out.println("2. PayPal");
        System.out.println("3. Transferencia Bancaria");
        System.out.println("4. Cheque");
        System.out.print("Seleccione una opción de pago: ");
        int opc = leer.nextInt();

        // Configurar la estrategia de pago en el carrito
        switch (opc) {
            case 1:
                carroCompra.setPagoStrategy(pagoTarjeta);
                break;
            case 2:
                carroCompra.setPagoStrategy(pagoPayPal);
                break;
            case 3:
                carroCompra.setPagoStrategy(pagoTransferencia);
                break;
            case 4:
                carroCompra.setPagoStrategy(pagoCheque);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        System.out.println("\n");
        // Realizar el pago
        System.out.print("Ingrese el monto a pagar: ");
        int cantidad = leer.nextInt();

        carroCompra.checkout(cantidad);

        leer.close();
    }
    
}
