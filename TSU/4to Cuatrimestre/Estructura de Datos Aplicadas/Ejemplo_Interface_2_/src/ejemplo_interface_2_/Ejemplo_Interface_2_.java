package ejemplo_interface_2_;

import java.util.Scanner;

public class Ejemplo_Interface_2_ {

    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int opc=0;
        Producto p=null;
        do{
            System.out.println("Seleccione una opcion");
            System.out.println("1.Altas");
            System.out.println("2.Bajas");
            System.out.println("3.Consultar");
            System.out.println("4.Modificar");
            System.out.println("5.Imprimir");
            System.out.print("Opcion: ");
            opc=s.nextInt();
            switch (opc) {
                case 1:
                    p=new Producto();
                    p.alta();
                    break;
                case 2:
                    p.baja();
                    break;
                case 3:
                    p.consulta();
                    break;
                case 4:
                    p.modificacion();
                    break;    
                case 5:
                    p.imprimir();
                    break;    
                default:
                    System.err.println("Opcion no valida");
            }
        }while(opc!=0);
    }
}
