package mx.edu.utez.redre.models.usuario;
import lombok.AllArgsConstructor; 
import lombok.Getter; 
import lombok.NoArgsConstructor; 
import lombok.Setter;
//import mx.edu.utez.redre.models.roles.Roles;

import javax.persistence.*;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
  
 @Entity 
 @Table(name = "usuarios") 
 @NoArgsConstructor 
 @AllArgsConstructor 
 @Setter 
 @Getter 
public class Usuario /*implements UserDetails */{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 100)
    private String correo;
    @Column(length = 100)
    private String password;
    
    //@ElementCollection(fetch = FetchType.EAGER)
    //List<Roles> roles;
    /*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
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
    }*/

}
