/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo_enumerodores;

import java.util.*;

/**
 *
 * @author pc
 */
public abstract class Vehiculo {
    
    private String codigo;
    private String Marca;
    private String Modelo;
    private int Anio;
    private String Color;
    static Scanner s=new Scanner(System.in);

    public Vehiculo() {
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

    @Override
    public String toString() {
        return "Vehiculo{" + "Codigo= " + codigo + "Marca=" + Marca + ", Modelo=" + Modelo + ", Anio=" + Anio + ", Color=" + Color + '}';
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
    
    public abstract Vehiculo alta();
    public abstract void baja();
    public abstract void modificacion();
    public abstract Vehiculo consulta();
}
