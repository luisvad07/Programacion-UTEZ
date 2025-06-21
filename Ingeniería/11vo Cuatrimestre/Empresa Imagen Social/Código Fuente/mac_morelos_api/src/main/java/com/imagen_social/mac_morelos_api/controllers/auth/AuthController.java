package com.imagen_social.mac_morelos_api.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imagen_social.mac_morelos_api.dtos.auth.LoginRequestDto;
import com.imagen_social.mac_morelos_api.dtos.auth.PasswordResetDto;
import com.imagen_social.mac_morelos_api.dtos.auth.PasswordResetRequestDto;
import com.imagen_social.mac_morelos_api.dtos.auth.UserTokenDto;
import com.imagen_social.mac_morelos_api.services.auth.AuthService;
import com.imagen_social.mac_morelos_api.utils.Response;

@RestController
@RequestMapping("${apiPrefix}/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<Response<UserTokenDto>> login(@RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }

    // Endpoint para logout
    @PostMapping("/logout")
    public Response<String> logout() {
        return authService.logout();
    }

    // Endpoint para solicitar restablecimiento de contraseña
    @PostMapping("/password-reset/request")
    public Response<Map<String, Object>>requestPasswordReset(@RequestBody PasswordResetRequestDto requestDto) {
        return authService.requestPasswordReset(requestDto);
    }

    // Endpoint para restablecer contraseña
    @PostMapping("/password-reset")
    public Response<String> resetPassword(@RequestBody PasswordResetDto resetDto) {

        String token = resetDto.getToken();

        // Verificar si el token es válido
        if (!authService.validateToken(token)) {
            // Crear un Response personalizado en lugar de ResponseEntity
            return new Response<>(null, true, 401, "Token inválido o ya utilizado.");
        }

        // Marcar el token como usado
        authService.markTokenAsUsed(token);

        return authService.resetPassword(resetDto.getToken(), resetDto.getNewPassword());
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<Map<String, Object>> validateResetToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        boolean isValid = authService.validateToken(token);

        Map<String, Object> response = new HashMap<>();
        if (!isValid) {
            response.put("error", true);
            response.put("message", "Token inválido o ya utilizado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("error", false);
        response.put("message", "Token válido.");
        return ResponseEntity.ok(response);
    }

}