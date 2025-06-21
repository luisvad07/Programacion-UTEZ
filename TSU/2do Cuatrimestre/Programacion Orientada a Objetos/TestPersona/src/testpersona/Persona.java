/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testpersona;

/**
 *
 * @author clientepreferido
 */
public class Persona {

    private String nombre;
    private int edad;
    private double estatura;
    private char sexo;
    private boolean estadoCivil;
///////////////////////////////////////////////////////////

    public Persona(String nombre, int edad, double estatura, char sexo, boolean estadoCivil) { //Constructor para inicializar variables
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
    }
///////////////////////////////////////////////////////////

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setEstadoCivil(boolean estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
///////////////////////////////////////////////////////////

    public String getNombre() {
        return nombre;
    }

    public int getedad() {
        return edad;
    }

    public double getEstatura() {
        return estatura;
    }

    public char getSexo() {
        return sexo;
    }

    public boolean isestadoCivil() {
        return estadoCivil;
    }

}
