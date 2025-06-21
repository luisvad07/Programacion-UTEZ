/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operaciones_aritmeticas;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Operaciones_aritmeticas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    double a, b, result;
    int num;
    Scanner teclado=new Scanner (System.in);
    System.out.printf("INTRODUCE EL NUMERO 1:");
    a=teclado.nextDouble();
    System.out.printf("INTRODUCE EL NUMERO 2:");
    b=teclado.nextDouble(); 
    System.out.println("Introduce el numero de la operacion que desea efectuar: 1- suma, 2- resta, 3-multiplicación, 4-división, 5-potencia y 6-raíz n-ésima");
    num=teclado.nextInt(); 
    switch (num) {
        case 1:
        result=a+b;
        System.out.printf("El resultado de la suma es:" + result);
        System.out.printf("Se realizo la suma correctamente");
        break;
        case 2:
        result=a-b;
        System.out.printf("El resultado de la resta es:" + result);
        System.out.printf("Se realizo la resta correctamente");
        break;    
        case 3:
        result=a*b;
        System.out.printf("El resultado de la multiplicacion es:" + result);
        System.out.printf("Se realizo la multiplicacion correctamente");
        break;
        case 4:
        result=a/b;
        System.out.printf("El resultado de la division es:" + result);
        System.out.printf("Se realizo la division correctamente");
        break;
        case 5:
        result=Math.pow(a, b);
        System.out.printf("El resultado de la potencia es:" + result);
        System.out.printf("Se realizo la potencia correctamente");
        break;
        case 6:
        result=(float) Math.pow(a, 1 / b);
        System.out.printf("El resultado de la raiz n-esima es:" + result);
        System.out.printf("Se realizo la raiz n-esima correctamente");
        break;    
        default: //Otra opcion
        System.out.printf("SYNTAX ERROR");
        break;    
        }    
    }
}
