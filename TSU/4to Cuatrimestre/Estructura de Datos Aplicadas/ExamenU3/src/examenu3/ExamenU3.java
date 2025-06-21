/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenu3;

import java.util.Scanner;

/**
 *
 * @author CC7
 */
public class ExamenU3 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner Bicho = new Scanner(System.in);
        double caja = 0;
        int cont = 0;
        double pagarTotal = 0;
        
        Productos proc[] = new Productos[10];
        Productos products = null;
        Ticket pacientes[]= new Ticket[cont];
        Ticket p = null;
        
        
        //Lista precargada de productos
        proc[0] = new Productos("Coca Cola","COCA600", 17.0, 10);
        proc[1] = new Productos("Papas Sabritas","SAB123", 12.0, 10);
        proc[2] = new Productos("Atun Dolores","ATN412", 18.0, 10);
        proc[3] = new Productos("Galletas Emperador","EMP400", 13.5, 10);
        proc[4] = new Productos("Salsa Valentina","VAL678", 23.5, 10);
        proc[5] = new Productos("Savile Chile","COCA600", 20.5, 10);
        proc[6] = new Productos("Frijoles La Sierra","FRI150", 18.0, 10);
        proc[7] = new Productos("Clamato","CLA666", 28.0, 10);
        proc[8] = new Productos("Cereal Nesquik","CER523", 32.0, 10);
        proc[9] = new Productos("Gomitas Pandita","GOM200", 10.0, 10);
        
        Listas ventas = new Listas();
        
        int opc;
        do {
            System.out.println("---- TIENDA DE ABARROTES LA MORENA ----");
            System.out.println("Selecciona una opción: ");
            System.out.println("1. Mostrar Productos");
            System.out.println("2. Registrar venta");
            System.out.println("3. Hacer una devolución");
            System.out.println("4. Gestionar Productos");
            System.out.println("5. Cerrar Caja");
            System.out.print("Opcion: ");
            opc = Bicho.nextInt();
            System.out.println("\n");
            switch (opc) {
                case 1:
                    System.out.println("    ---- PRODUCTOS EN VENTA ----       ");
                    for (Productos proc1 : proc) {
                        System.out.println(proc1);
                    }
                    break;
                case 2:
                    String e;
                    do{
                        System.out.println("Ingrese el Codigo del Producto... | -1. Terminar Venta");
                        System.out.print("Opcion: ");
                        e = Bicho.next();
                        for (Productos proc1 : proc) {
                            if (e.equals(proc1.getCodigo())) {
                                System.out.println("Nombre del Producto " + proc1.getNombre());
                                System.out.println("Precio del Producto " + proc1.getPrecio());
                                if (confirmarCambio()==1) {
                                    cont++;
                                    System.out.println("La compra se ha realizado con éxito!");
                                    caja=cont*proc1.getPrecio();
                                    System.out.println("Total -> "+caja);
                                    System.out.println("Numero de Productos -> "+cont);
                                    p = new Ticket(caja, cont);
                                    System.out.println("--Producto Agregado!--");     
                                } else {
                                    System.out.println("Producto no agregado!");
                                }
                                break;          
                            } else {
                                if (e.equals("-1")) {
                                    if (confirmarCambio()==1) {
                                        System.out.println("Venta registrada!");
                                        ventas.insertar(p);
                                        pagarTotal = pagarTotal + caja;
                                        break;
                                    } else {
                                        System.out.println("Venta no agregada");
                                        break;
                                    }
                                } else {
                                    System.out.println("Producto No encontrado!");
                                    break;
                                }
                            }
                        }
                    }while(!"-1".equals(e));
                    break;
                case 3:
                    System.out.print("Folio de Venta que deseas buscar: ");
                    int n = Bicho.nextInt();
                    if (confirmarCambio()==1) {
                        ventas.eliminarPos(n);
                        System.out.println("Numero " + n + " eliminado correctamente!");
                    } else {
                        System.out.println("Cambios no guardados!...");
                    }
                    break;
                case 4:
                    break;   
                case 5:
                    System.out.println("Saliendo....");
                    ventas.imprimir();
                    System.out.println("Ventas totales: "+pagarTotal);
                    break;      
                default:
                    System.out.println("Error de Opcion!");;
            }
            System.out.println("\n");
        } while (opc != 5);
    }
    
    public static int confirmarCambio() {
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("1.- CONFIRMAR");
        System.out.println("2.- CANCELAR");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp;
    }
}
   
