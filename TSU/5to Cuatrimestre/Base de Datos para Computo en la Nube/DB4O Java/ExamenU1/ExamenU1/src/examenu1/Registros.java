/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenu1;

/**
 *
 * @author Luis Valladolid
 */
public class Registros {
    
    private String id;
    private String nombre;
    private String apellidos;
    private String teléfono;
    private String edad;
    private String tipo_sangre;
    private String email;
    private String carrera;
    private String promedio;

    public Registros(String id, String nombre, String apellidos, String teléfono, String edad, String tipo_sangre, String email, String carrera, String promedio) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.teléfono = teléfono;
        this.edad = edad;
        this.tipo_sangre = tipo_sangre;
        this.email = email;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    public Registros() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(String teléfono) {
        this.teléfono = teléfono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return id + " \t | " + nombre + " \t | " +  apellidos + " \t | " + teléfono + " \t | " + edad + " \t | " + tipo_sangre + " \t | " + email + " \t | " + carrera + " \t | " + promedio;
    }   
    
}
