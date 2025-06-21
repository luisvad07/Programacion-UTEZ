package com.imagen_social.mac_morelos_api.dtos.auth;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {
    @NotBlank
    private String token; // Token de restablecimiento enviado al correo
    @NotBlank
    private String newPassword; // Nueva contraseña
}

