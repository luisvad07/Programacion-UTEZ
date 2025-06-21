package mx.edu.utez.MiProyectoServlet.model.auth;

public class BeanAuth {
    private Long idP;
    private Long idU;
    private String fulname;
    private String email;
    private String image;
    private String username;
    private String role;

    public BeanAuth(){

    }

    public BeanAuth(Long idP, Long idU, String fulname, String email, String image, String username, String role) {
        this.idP = idP;
        this.idU = idU;
        this.fulname = fulname;
        this.email = email;
        this.image = image;
        this.username = username;
        this.role = role;
    }

    public Long getIdP() {
        return idP;
    }

    public void setIdP(Long idP) {
        this.idP = idP;
    }

    public Long getIdU() {
        return idU;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }

    public String getFulname() {
        return fulname;
    }

    public void setFulname(String fulname) {
        this.fulname = fulname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
