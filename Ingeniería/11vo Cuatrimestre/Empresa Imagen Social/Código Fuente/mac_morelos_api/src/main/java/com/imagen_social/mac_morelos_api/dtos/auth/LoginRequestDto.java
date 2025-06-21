package com.imagen_social.mac_morelos_api.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String identifier; // Puede ser username o email
    @NotBlank
    private String password;
}
