/*package mx.edu.utez.redre.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mx.edu.utez.redre.models.usuario.Usuario;
import mx.edu.utez.redre.models.departamento.*;


public class UsuarioAuth implements UserDetails{
    private String correo;
    private String password;
    private Departamento departamento;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getCorreo() {
        return correo;
    }

    public UsuarioAuth() {
    }

    public UsuarioAuth(String correo, String password, Departamento departamento, Collection<? extends GrantedAuthority> authorities) {
        this.correo = correo;
        this.password = password;
        this.departamento = departamento;
        this.authorities = authorities;
    }

    public static UsuarioAuth build(Usuario usuario) {

        List<GrantedAuthority> authority = usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getAccount()))
                .collect(Collectors.toList());

        return new UsuarioAuth(usuario.getCorreo(), usuario.getPassword(), usuario.getDepartamento(), authority);
    }

    public Departamento getDepartamento(){
        return departamento;
    }

    public void setDepartamento(Departamento departamento){
        this.departamento = departamento;
    }

    /*public Responsables getResponsable(){
        return responsable;
    }

    public void setResponsable(Responsables responsable){
        this.responsable = responsable;
    }

    public Asesor getAsesor(){
        return asesor;
    }

    public void setAsesor(Asesor asesor){
        this.asesor = asesor;
    }

    public Estudiantes getEstudiantes(){
        return estudiante;
    }

    public void setEstudiantes(Estudiantes estudiante){
        this.estudiante = estudiante;
    }
}*/
