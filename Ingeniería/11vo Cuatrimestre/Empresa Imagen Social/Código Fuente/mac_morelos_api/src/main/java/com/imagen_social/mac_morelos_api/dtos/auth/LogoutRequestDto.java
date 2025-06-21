package com.imagen_social.mac_morelos_api.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    @NotBlank
    private String token; // Token actual del usuario
}