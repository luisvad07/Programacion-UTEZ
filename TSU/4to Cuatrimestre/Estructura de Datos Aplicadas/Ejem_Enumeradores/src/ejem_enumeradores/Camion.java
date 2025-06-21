/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejem_enumeradores;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Camion implements Vehiculo {

    private String codigo;
    private String Marca;
    private String Modelo;
    private int Anio;
    private String Color;
    int num_puertas;
    String tipo_camion;
    String busqueda;
    int opc;
    
    public Camion() {
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int Anio) {
        this.Anio = Anio;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }
    
    public int getNum_puertas() {
        return num_puertas;
    }

    public void setNum_puertas(int num_puertas) {
        this.num_puertas = num_puertas;
    }

    public String getTipo_camion() {
        return tipo_camion;
    }

    public void setTipo_camion(String tipo_camion) {
        this.tipo_camion = tipo_camion;
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
        return "Auto{" + "Codigo= " + codigo + "Marca=" + Marca + ", Modelo=" + Modelo + ", Anio=" + Anio + ", Color=" + Color + ", num_puertas=" + num_puertas + ", tipo_auto=" + tipo_camion + "}";
    }
    
    @Override
    public void alta() {
        this.s.useDelimiter("\n");
            System.out.print("Introduzca Codigo del Vehiculo: ");
            this.setCodigo(s.nextLine());
            System.out.print("Introduzca Marca: ");
            this.setMarca(s.nextLine());
            System.out.print("Introduzca Modelo: ");
            this.setModelo(s.nextLine());
            System.out.print("Introduzca Color: ");
            this.setColor(s.nextLine());
            System.out.print("Introduzca Tipo de Auto: ");
            this.setTipo_camion(s.nextLine());
            System.out.print("Introduzca Anio: ");
            this.setAnio(s.nextInt());
            System.out.print("Introduzca Numero de puertas: ");
            this.setNum_puertas(s.nextInt());
    }

    @Override
    public void baja() {
        Iterator i=vehiculos.iterator();
        while(i.hasNext()){
            System.out.println("Vehiculo: "+this.getCodigo() +" de Marca " +this.getMarca()+ ", está en la posicion del ArrayList " + i);
                System.out.println("Se eliminara!");
                boolean change = confirmarCambio();
                if (change) {
                    vehiculos.remove(i);
                    System.out.println("Se ha realizado exitosamente la Modificacion...");
                } else {
                    System.out.println("Cambios no guardados!...");
                }
        }
    }

    @Override
    public void modificacion() {
        Iterator i=vehiculos.iterator();
        while(i.hasNext()){
            System.out.println("Modificacion de Vehiculo: "+this.getCodigo() +", está en la posicion del ArrayList " + i);
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
                                        this.setTipo_camion(s.next());
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
        }
    }

    @Override
    public void consulta() {
    System.out.print("Ingresa el Codigo del Auto que deseas buscar: ");
        this.setBusqueda(s.next());
        int existe = this.getBusqueda().indexOf(this.getCodigo());
            if (existe!= -1) {
		System.out.println("Codigo: " + this.getCodigo());
                System.out.println("Marca: " + this.getMarca());
                System.out.println("Modelo: " + this.getModelo());
                System.out.println("Tipo Camion: " + this.getTipo_camion());
                System.out.println("Anio: " + this.getAnio());
                System.out.println("Color: " + this.getColor());
                System.out.println("No. Puertas: " + this.getNum_puertas());
            } else {
		System.out.println("El elemento no existe");
	}
    }
    
    public static boolean confirmarCambio() { //Metodo para confirmar el cambio de la modificacion de un campo
        int resp;
        Scanner siuu = new Scanner(System.in);
        System.out.println("¿Estas seguro de realizar el cambio?");
        System.out.println("1.- Si");
        System.out.println("2.- No");
        System.out.print("Respuesta: ");
        resp = siuu.nextInt();
        siuu.skip("\n");
        return resp == 1; //Si es Verdadero acepta el cambio, Si es Falso lo rechaza
    }
}
