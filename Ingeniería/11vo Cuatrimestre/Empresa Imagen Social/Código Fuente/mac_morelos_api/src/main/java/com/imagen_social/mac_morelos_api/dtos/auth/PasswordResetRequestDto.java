package com.imagen_social.mac_morelos_api.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDto {
    @NotBlank
    @Email
    private String email; // Email del usuario para enviar el enlace de restablecimiento
}