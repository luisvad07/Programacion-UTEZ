/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenu3;

/**
 *
 * @author CC7
 */
public class Productos {
    
    private String nombre; 
    private String codigo;
    private double precio;
    private int num_existencias;

    public Productos(String nombre, String codigo, double precio, int numero_existencias) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
        this.num_existencias = numero_existencias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getNum_existencias() {
        return num_existencias;
    }

    public void setNum_existencias(int numero_existencias) {
        this.num_existencias = numero_existencias;
    }

    @Override
    public String toString() {
        return "Productos{" + "nombre=" + nombre + ", codigo=" + codigo + ", precio=" + precio + ", num_existencias=" + num_existencias + '}';
    }
    
    public int disminuir(int num) {
        num_existencias = num_existencias - num;
        return num_existencias;
    }
}
