/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nocircula;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class Nocircula {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    int placa;
    Scanner teclado=new Scanner (System.in);
    System.out.println("Ingrese el numero de terminacion de placa (Solo un digito, del 0 al 9)");
    placa=teclado.nextInt();
    if (placa <=9) {
    switch (placa){
        case 1: //Lunes & Amarillo
        System.out.println ("Es lunes y el color de su calcomanía es amarillo, por lo tanto hoy no circula de acuerdo a su informacion");
        break;
        case 2 : //Lunes & Amarillo
        System.out.println ("Es lunes y el color de su calcomanía es amarillo, por lo tanto hoy no circula de acuerdo a su informacion");
        break;    
        case 3: //Martes & Rosa
        System.out.println ("Es martes y el color de su calcomanía es rosa, por lo tanto hoy no circula de acuerdo a su informacion"); 
        break;
        case 4: //Martes & Rosa
        System.out.println ("Es martes y el color de su calcomanía es rosa, por lo tanto hoy no circula de acuerdo a su informacion"); 
        break;    
        case 5: //Miercoles & Rojo
        System.out.println ("Es miercoles y el color de su calcomanía es rojo, por lo tanto hoy no circula de acuerdo a su informacion");
        break;
        case 6: //Miercoles & Rojo
        System.out.println ("Es miercoles y el color de su calcomanía es rojo, por lo tanto hoy no circula de acuerdo a su informacion");
        break;    
        case 7: //Jueves y Verde
        System.out.println ("Es jueves y el color de su calcomanía es verde, por lo tanto hoy no circula de acuerdo a su informacion");
        break;
        case 8: //Jueves y Verde
        System.out.println ("Es jueves y el color de su calcomanía es verde, por lo tanto hoy no circula de acuerdo a su informacion");
        break;    
        case 9: //Viernes & Azul
        System.out.println ("Es viernes y el color de su calcomanía es azul, por lo tanto hoy no circula de acuerdo a su informacion");
        break;
        case 0: //Viernes & Azul
        System.out.println ("Es viernes y el color de su calcomanía es azul, por lo tanto hoy no circula de acuerdo a su informacion");
        break;    
        default: //Otra opcion
        System.out.printf("Como su informacion es diferente, por lo tanto, cualquier dia se circula");
        break;
    }    
    }
}
}
    
