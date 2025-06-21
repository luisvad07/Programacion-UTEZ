/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo_enumerodores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author pc
 */
public class Auto extends Vehiculo {
    
    int num_puertas;
    String tipo_auto;
    String busqueda;
    int opc;

    public Auto() {
    }

    public int getNum_puertas() {
        return num_puertas;
    }

    public void setNum_puertas(int num_puertas) {
        this.num_puertas = num_puertas;
    }

    public String getTipo_auto() {
        return tipo_auto;
    }

    public void setTipo_auto(String tipo_auto) {
        this.tipo_auto = tipo_auto;
    }
    
    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public int getOpc() {
        return opc;
    }

    public void setOpc(int opc) {
        this.opc = opc;
    }
    
    

    @Override
    public String toString() {
        return super.toString() + " Auto{ " + ", num_puertas=" + num_puertas + ", tipo_auto=" + tipo_auto + '}';
    }
    

    @Override
    public Vehiculo alta() {
        try {
            System.out.print("Introduzca Codigo del Vehiculo: ");
            this.setCodigo(s.nextLine());
            System.out.print("Introduzca Marca: ");
            this.setMarca(s.nextLine());
            System.out.print("Introduzca Modelo: ");
            this.setModelo(s.nextLine());
            System.out.print("Introduzca Color: ");
            this.setColor(s.nextLine());
            System.out.print("Introduzca Tipo de Auto: ");
            this.setTipo_auto(s.nextLine());
            System.out.print("Introduzca Anio: ");
            this.setAnio(s.nextInt());
            System.out.print("Introduzca Numero de puertas: ");
            this.setNum_puertas(s.nextInt());
        } catch (Exception e) {
            System.err.println("Ocurrio un error durante la captura de los datos");
            System.out.println("Volviendo a solicitar los datos");
            System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
            System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
            System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
            System.out.println("");
            alta();
        }
        return this;
    }

    @Override
    public void baja() {
        
    }

    @Override
    public void modificacion() { 
        System.out.print("Ingresa la Marca del Auto que deseas buscar: ");
        this.setBusqueda(s.next());
        int indice = this.getBusqueda().indexOf(this.getMarca());
        if (indice != -1) {
            System.out.println("Modificacion de Vehiculo: "+this.getMarca() +", está en la posicion del ArrayList " + indice);
                        System.out.println("Selecciona la operacion que desea efectuar:");
                        System.out.println("1.- Modificar Marca\n"
                                + "2.- Modificar Modelo\n"
                                + "3.- Modificar Anio\n"
                                + "4.- Modificar Color\n"
                                + "5.- Modificar Tipo de Auto\n"
                                + "6.- Modificar No. Puertas\n"
                                + "7.- Salir");
                        System.out.print("Opcion: ");
                        this.setOpc(s.nextInt());
                            switch (this.getOpc()) {
                                case 1: 
                                    boolean change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa el nuevo nombre de la Marca: ");
                                        this.setMarca(s.next());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;
                                case 2: 
                                    change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa el nuevo nombre del Modelo: ");
                                        this.setModelo(s.next());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;    
                                case 3: 
                                    change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa el nuevo Anio del Auto:");
                                        this.setAnio(s.nextInt());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;    
                                case 4:
                                    change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa el nuevo Color del Auto:");
                                        this.setColor(s.next());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;
                                case 5:
                                    change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa el nuevo Tipo de Auto:");
                                        this.setTipo_auto(s.next());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break; 
                                case 6:
                                    change = confirmarCambio();
                                    if (change) {
                                        System.out.print("Ingresa nuevamente el No. de Puertas:");
                                        this.setNum_puertas(s.nextInt());
                                        System.out.println("Modificacion Realizada...");
                                    } else {
                                        System.out.println("Cambios no guardados!...");
                                    }
                                    break;     
                                case 7: //Regresar al menu principal
                                    System.out.println("Regresando...");
                                    break;
                                default:
                                    System.out.println("Error de Opcion!");
                                    break;
                                    }
                        } else {
                            System.out.println("Los datos no son correctos!");
                        }
   }

    @Override
    public Vehiculo consulta() {
            System.out.println("Codigo: " + this.getCodigo());
            System.out.println("Marca: " + this.getMarca());
            System.out.println("Modelo: " + this.getModelo());
            System.out.println("Tipo Auto: " + this.getTipo_auto());
            System.out.println("Anio: " + this.getAnio());
            System.out.println("Color: " + this.getColor());
            System.out.println("No. Puertas: " + this.getNum_puertas());
        return this;
    }
}