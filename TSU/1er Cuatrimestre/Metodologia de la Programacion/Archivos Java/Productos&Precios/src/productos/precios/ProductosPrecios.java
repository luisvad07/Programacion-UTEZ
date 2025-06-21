/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productos.precios;
import java.util.Scanner;
/**
 *
 * @author CC-6
 */
public class ProductosPrecios {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int num=0;
    String product[] = new String [0];
    double precios[] = new double [0];
    Scanner teclado=new Scanner (System.in);
        System.out.println("Ingresa la cantidad de productos: ");
        num=teclado.nextInt();
        product = new String [num];
        precios = new double [num];
    for(int a=0;a<product.length;a++){
        System.out.println("Ingresa el nombre de un producto: ");
        teclado.nextLine();
        product[a]=teclado.nextLine();
        System.out.println("Ingresa el precio del producto");
        precios[a]=teclado.nextDouble();
    }
    for(int a=0;a<product.length;a++){
        System.out.println("Lista de productos con sus precios");
        System.out.println(product[a] + "..............." + precios[a]);
    }
  }
}
