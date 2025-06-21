package utez.edu.mx.foodster.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuarios user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toSet());
        return new UserDetailsImpl(user.getCorreo(), user.getContrasena(), user.getActive(), authorities);
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
        return username;
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

