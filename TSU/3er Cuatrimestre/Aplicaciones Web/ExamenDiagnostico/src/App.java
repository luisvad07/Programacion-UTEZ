import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        
        //Llama la clase Arreglos
        Persona []array = new Persona[5];
        
        System.out.println("------Formulario de Ingreso de Datos--------");
        
        for (int i=0; i < array.length; i++) {
            array[i] = new Persona();
            System.out.println("Persona: " + (i+1));
            System.out.print("Ingrese su nombre: ");
            array[i].setNombre(teclado.nextLine());
            System.out.print("Ingrese su edad: ");
            array[i].setEdad(teclado.nextInt());
            System.out.print("Ingrese su sexo: ");
            array[i].setSexo(teclado.next().charAt(0));
        teclado.nextLine();
        System.out.println("\n");    
        }

        for (int i=0; i < array.length; i++) {
            System.out.println("---------DATOS DE LA PERSONA "+ (i+1) +"---------");
            System.out.println("Su nombre es: " + array[i].getNombre());
            System.out.println("Su edad es: " + array[i].getEdad());
            System.out.println("Su sexo es: " + array[i].getSexo());
            array[i].mostrarInformacion();
        }
        
    }
}
