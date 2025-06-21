package utez.tienda.tiendautez.administrator.model;

public class AdminBean {

    private int idAdmin;
    private String username;
    private String email;
    private String psw;
    private int id_rol;
    private String rol;


    public AdminBean() {
    }

    public AdminBean(String username, String email, String psw,int  id_rol) {
        this.username = username;
        this.email = email;
        this.psw = psw;
        this.id_rol = id_rol;
    }

    public AdminBean( String email, String psw) {
        this.psw = psw;
        this.email = email;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public AdminBean(int idAdmin, String username, String email, int id_rol, String rol) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.email = email;
        this.id_rol = id_rol;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return "AdminBean{" +
                "idAdmin=" + idAdmin +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", psw='" + psw + '\'' +
                ", id_rol=" + id_rol +
                ", rol='" + rol + '\'' +
                '}';
    }
}
