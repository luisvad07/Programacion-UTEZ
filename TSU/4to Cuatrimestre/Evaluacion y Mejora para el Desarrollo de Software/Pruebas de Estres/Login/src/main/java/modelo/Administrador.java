package modelo;

public class Administrador {
    String nombre;
    String correo;
    String password;
//Por cada objeto que vayamos a utilizar necesitamos una clase con sus atributos
    public Administrador() {
    }

    public Administrador(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    public Administrador(String nombre, String correo, String password) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
