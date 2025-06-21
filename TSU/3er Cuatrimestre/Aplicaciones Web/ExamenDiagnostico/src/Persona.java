public class Persona {
    private String nombre;
    private int edad;
    private char sexo;

    public Persona() { //Constructor vacio
        
    }

    public Persona(String nombre, int edad, char sexo) { //Constructor para inicializar variables
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void mostrarInformacion() {
        StringBuilder strb = new StringBuilder(this.nombre);
		String nombreReves = strb.reverse().toString();

        String sexoCompleto;
        if (this.sexo == 'H') {
            sexoCompleto = "Hombre";
        } else {
            sexoCompleto = "Mujer";
        }

        int anioNac = 2022-this.edad;
        System.out.println("Nombre al reves: " + nombreReves);
        System.out.println("Año de Nacimiento: " + anioNac);
        System.out.println("Sexo: " + sexoCompleto);
    }
}
