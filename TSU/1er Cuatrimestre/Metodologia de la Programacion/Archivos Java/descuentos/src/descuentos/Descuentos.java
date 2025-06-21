/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descuentos;
import java.util.Scanner;

/**
 *
 * @author clientepreferido
 */
public class Descuentos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    double compra, descu = 0, total;
    int color;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingresa el total de la compra realizada");
    compra=teclado.nextDouble(); 
    System.out.println("De acuerdo a estas opciones selecciona una bola: 1. Verde, 2. Amarillo, 3. Rojo, 4. Blanco");
    color=teclado.nextInt(); 
    switch (color) {
        case 1:
        descu = compra*0.30;
        break;
        case 2:
        descu = compra*0.20;
        break;    
        case 3:
        descu = compra*0.10; 
        break;
        case 4:
        descu = 0; 
        break;
        default: //Otra opcion
        System.out.printf("El color de bola no existe");
        break;    
        }
    total = compra-descu;
    System.out.println("El total a pagar ya con el descuento aplicado es: $" + total );
    System.out.println("El descuento aplicado es: $" + descu);
    }
    
}
