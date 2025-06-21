package com.imagen_social.mac_morelos_api.security.entity;

import com.imagen_social.mac_morelos_api.enums.allowedPlatforms.AllowedPlatforms;
import com.imagen_social.mac_morelos_api.models.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private String username;  // Aquí se guardará el username real (e.g., "oscarB")
    private String email;     // Aquí se guardará el email (e.g., "20213tn002@utez.edu.mx")
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;
    private AllowedPlatforms allowedPlatforms;  // Nuevo campo para las plataformas permitidas

    // Constructor
    public UserDetailsImpl(String username, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, AllowedPlatforms allowedPlatforms) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
        this.allowedPlatforms = allowedPlatforms;  // Asignar allowedPlatforms
    }

    // Método estático para construir UserDetailsImpl desde un objeto User
    public static UserDetailsImpl build(User user) {
        // Asignar el username solo si el usuario tiene un username real
        String username = user.getUsername(); 
        
        // Crear la lista de autoridades con base en el rol
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name()));
        
        // Obtener los allowedPlatforms desde el rol del usuario
        AllowedPlatforms allowedPlatforms = user.getRole().getAllowedPlatforms();
        
        // Crear y retornar el UserDetailsImpl con los datos necesarios
        return new UserDetailsImpl(
            username,                         // username será null si no es un CIUDADANO
            user.getEmail(),                  // email del usuario
            user.getPassword(),               // password del usuario
            user.getStatus(),                 // estado (activo/inactivo) del usuario
            authorities,                      // roles/autorizaciones del usuario
            allowedPlatforms                  // allowedPlatforms del rol
        );
    }    

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
        return this.username != null ? this.username : this.email;
    }

    public String getEmail() {
        return email;
    }

    public AllowedPlatforms getAllowedPlatforms() {  // Getter para allowedPlatforms
        return allowedPlatforms;
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
        return enabled;
    }
}