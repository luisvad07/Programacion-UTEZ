package server;

import java.util.Date;

public class BeanCURP {

    //Varibales
    private long id;
    private String Nombre;
    private String Apellido_Pat;
    private String Apellido_Mat;
    private String Sexo;
    private String Estado_Nac;
    private Date Fecha_Nac;
    private String CURP;

    //Constructor Vacio
    public BeanCURP() {

    }

    //Constructor


    public BeanCURP(long id, String nombre, String apellido_Pat, String apellido_Mat, String sexo, String estado_Nac, Date fecha_Nac, String CURP) {
        this.id = id;
        Nombre = nombre;
        Apellido_Pat = apellido_Pat;
        Apellido_Mat = apellido_Mat;
        Sexo = sexo;
        Estado_Nac = estado_Nac;
        Fecha_Nac = fecha_Nac;
        this.CURP = CURP;
    }

    //Getter and Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido_Pat() {
        return Apellido_Pat;
    }

    public void setApellido_Pat(String apellido_Pat) {
        Apellido_Pat = apellido_Pat;
    }

    public String getApellido_Mat() {
        return Apellido_Mat;
    }

    public void setApellido_Mat(String apellido_Mat) {
        Apellido_Mat = apellido_Mat;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getEstado_Nac() {
        return Estado_Nac;
    }

    public void setEstado_Nac(String estado_Nac) {
        Estado_Nac = estado_Nac;
    }

    public Date getFecha_Nac() {
        return Fecha_Nac;
    }

    public void setFecha_Nac(Date fecha_Nac) {
        Fecha_Nac = fecha_Nac;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }
}
