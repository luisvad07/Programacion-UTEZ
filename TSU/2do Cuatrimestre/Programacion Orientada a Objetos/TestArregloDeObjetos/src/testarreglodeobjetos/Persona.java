/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testarreglodeobjetos;

/**
 *
 * @author clientepreferido
 */
public class Persona {

    private String clave;
    private String nombre;
    private int edad;
    private double estatura;

    public Persona() {
        nombre = "";
        clave = "";
        edad = 0;
        estatura = 0.0;
    }

    public Persona(String clave, String nombre, int edad, double estatura) {
        this.clave = clave;
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
    }
    
     @Override
    public String toString() {
        return "Persona{" + "clave=" + clave + ", nombre=" + nombre + ", edad=" + edad + ", estatura=" + estatura + '}';
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getEstatura() {
        return estatura;
    }

}
