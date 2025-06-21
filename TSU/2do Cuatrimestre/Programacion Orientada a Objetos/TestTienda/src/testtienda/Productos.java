/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtienda;

/**
 *
 * @author clientepreferido
 */
public class Productos {

    private String nombre;
    private double precio;

    private int existencia;

    public Productos(String nombre, double precio, int existencia) {
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;

    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", precio=" + precio + ", existencia=" + existencia + '}';
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void aumentarExistencia(int stock) {

        this.existencia += stock;

    }

    public void disminuirExistencia(int stock) {
        this.existencia -= stock;

    }
}
