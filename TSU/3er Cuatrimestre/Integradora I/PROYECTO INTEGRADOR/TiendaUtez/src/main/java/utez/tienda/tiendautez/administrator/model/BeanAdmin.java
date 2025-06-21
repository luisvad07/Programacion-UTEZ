package utez.tienda.tiendautez.administrator.model;

public class BeanAdmin {
    private Long id_admins;
    private String username;
    private String psw;
    private String email;
    private Long roles_id_roles;
    private Long id_roles;
    private String rol;

    public BeanAdmin() {
    }

    public BeanAdmin(Long id_admins, String username, String psw, String email, Long roles_id_roles, Long id_roles, String rol) {
        this.id_admins = id_admins;
        this.username = username;
        this.psw = psw;
        this.email = email;
        this.roles_id_roles = roles_id_roles;
        this.id_roles = id_roles;
        this.rol = rol;
    }

    public Long getId_admins() {
        return id_admins;
    }

    public void setId_admins(Long id_admins) {
        this.id_admins = id_admins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoles_id_roles() {
        return roles_id_roles;
    }

    public void setRoles_id_roles(Long roles_id_roles) {
        this.roles_id_roles = roles_id_roles;
    }

    public Long getId_roles() {
        return id_roles;
    }

    public void setId_roles(Long id_roles) {
        this.id_roles = id_roles;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
