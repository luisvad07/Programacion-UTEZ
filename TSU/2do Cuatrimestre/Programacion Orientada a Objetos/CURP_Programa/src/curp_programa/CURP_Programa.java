/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curp_programa;
import java.util.Scanner;
/**
 *
 * @author clientepreferido
 */
public class CURP_Programa {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Scanner scanner = new Scanner(System.in);
 
		System.out.println("Nombre(s): ");
		String nombre = scanner.nextLine();
 
		System.out.println("Primer Apellido: ");
		String ape1 = scanner.nextLine();
 
		System.out.println("Segundo Apellido: ");
		String ape2 = scanner.nextLine();
 
		System.out.println("Dia de nacimiento (dos caracteres): ");
		String dia = scanner.nextLine();
 
		System.out.println("Mes de nacimiento (dos caracteres): ");
 		String mes = scanner.nextLine();
 
 		System.out.println("Año de nacimiento (4 caracteres): ");
		String anio = scanner.nextLine();
                
                System.out.println("Sexo (Mujer/Hombre): ");
		String sexo = scanner.nextLine();
                
                
                System.out.println("Naciste el día: "+dia.substring(8,10));
		System.out.println("En el mes : "+mes.substring(6,18));
		System.out.println("Del año: "+anio.substring(4,6));
                
                System.out.println("Tu CURP es: ");
                
		
    }
}


