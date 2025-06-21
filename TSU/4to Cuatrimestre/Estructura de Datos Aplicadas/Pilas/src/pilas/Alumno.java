/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pilas;

/**
 *
 * @author Luis Valladolid
 */
public class Alumno {
   private String Nombre;
   private int Edad;
   PilaEnlazada pilaCalificaciones;

    public Alumno() {
        pilaCalificaciones=new PilaEnlazada();
    }

    public Alumno(String Nombre, int Edad) {
        this.Nombre = Nombre;
        this.Edad = Edad;
        pilaCalificaciones=new PilaEnlazada();
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public PilaEnlazada getPilaCalificaciones() {
        return pilaCalificaciones;
    }

    public void setPilaCalificaciones(PilaEnlazada pilaCalificaciones) {
        this.pilaCalificaciones = pilaCalificaciones;
    }

    @Override
    public String toString() {
        return "Nombre:" + Nombre + ", Edad:" + Edad + ", Calificaciones[" + pilaCalificaciones + "]";
    } 
}
