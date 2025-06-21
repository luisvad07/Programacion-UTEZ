package com.imagen_social.mac_morelos_api.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token; // JWT
    private String username;
    private String role;
    private String platform;
}
