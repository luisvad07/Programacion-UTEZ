package com.imagen_social.mac_morelos_api.dtos.auth;

import org.springframework.security.core.userdetails.UserDetails;

import com.imagen_social.mac_morelos_api.enums.allowedPlatforms.AllowedPlatforms;
import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.security.entity.UserDetailsImpl;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDto {
    @NotBlank
    private String token; // Token JWT del usuario
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private RoleEnum role; // Rol del usuario
    @NotBlank
    private AllowedPlatforms allowedPlatforms;

    // Nuevo constructor con los parámetros correctos
    public UserTokenDto(String token, String username, String email, RoleEnum role, AllowedPlatforms allowedPlatforms) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
        this.allowedPlatforms = allowedPlatforms;
    }

    // Constructor con los campos requeridos
    public UserTokenDto(String token, UserDetails userDetails) {
        this.token = token;
        // Verifica que el userDetails es de tipo UserDetailsImpl para acceder al email
        if (userDetails instanceof UserDetailsImpl) {
            UserDetailsImpl ud = (UserDetailsImpl) userDetails;
            this.username = ud.getUsername();
            this.email = ud.getEmail();
        } else {
            // Fallback: en caso de que no se trate de un UserDetailsImpl
            this.username = userDetails.getUsername();
            this.email = userDetails.getUsername();
        }
        // Obtén el rol del objeto UserDetails (esto depende de cómo estés manejando los roles en tu implementación)
        this.role = RoleEnum.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());
        // Ahora, asumiendo que 'allowedPlatforms' es algo que debes derivar de los roles
        // Aquí puedes modificar esta lógica según como manejes las plataformas permitidas.
        if (userDetails instanceof UserDetailsImpl) {
            UserDetailsImpl ud = (UserDetailsImpl) userDetails;
            this.allowedPlatforms = ud.getAllowedPlatforms(); // Si tienes un método para obtener este valor
        }
    }
}
