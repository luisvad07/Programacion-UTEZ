package com.imagen_social.mac_morelos_api.services.auth;

import com.imagen_social.mac_morelos_api.dtos.auth.LoginRequestDto;
import com.imagen_social.mac_morelos_api.dtos.auth.PasswordResetRequestDto;
import com.imagen_social.mac_morelos_api.dtos.auth.UserTokenDto;
import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.models.users.User;
import com.imagen_social.mac_morelos_api.models.users.UserRepository;
import com.imagen_social.mac_morelos_api.security.jwt.JwtProvider;
import com.imagen_social.mac_morelos_api.utils.Response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {

    // Crear un Logger estático para la clase
    private static final Logger log = Logger.getLogger(AuthService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender emailSender;  // Suponiendo que tienes un servicio de correo
    private final ThymeleafViewResolver thymeleafViewResolver;  // Usar Thymeleaf para renderizar plantillas
    private final Set<String> usedTokens = new HashSet<>(); // Almacena tokens usados temporalmente

    // Método para codificar el token en Base64
    private String encodeToken(String token) {
        return Base64.getUrlEncoder().encodeToString(token.getBytes());
    }

    // Método para login
    @Transactional(readOnly = true)
    public ResponseEntity<Response<UserTokenDto>> login(LoginRequestDto dto) {
        try {
            // Primero, se busca el usuario por su rol y el identificador correspondiente
            Optional<User> optionalUser;
            
            // Verificamos el rol antes de decidir si buscamos por email o username
            if (dto.getIdentifier().contains("@")) {
                optionalUser = userRepository.findByEmail(dto.getIdentifier());
            } else {
                optionalUser = userRepository.findByUsername(dto.getIdentifier());
            }

            // Verificar si se encontró el usuario
            User user = optionalUser.orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

            // Verificar si el usuario está activo
            if (!user.getStatus()) {
                return new ResponseEntity<>(new Response<>(null, true, 403, "Este usuario está inactivo. Contacta al administrador."), HttpStatus.FORBIDDEN);
            }

            // Verificar si la contraseña es correcta
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(new Response<>(null, true, 401, "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED);
            }

            // Verificar el rol y el identificador (username o email)
            switch (user.getRole().getName()) {
                case CIUDADANO:
                    if (!dto.getIdentifier().equals(user.getUsername())) {
                        return new ResponseEntity<>(new Response<>(null, true, 401, "Acceso Denegado. ¡Eres Ciudadano!"), HttpStatus.UNAUTHORIZED);
                    }
                    break;
                case ADMINISTRADOR:
                case PROMOTOR:
                case PERIODISTA:
                case SUPERVISOR:
                    if (!dto.getIdentifier().equals(user.getEmail())) {
                        return new ResponseEntity<>(new Response<>(null, true, 401, "Solo los ciudadanos pueden ingresar a la App Móvil."), HttpStatus.UNAUTHORIZED);
                    }
                    break;
                default:
                    return new ResponseEntity<>(new Response<>(null, true, 401, "Rol no permitido/autorizado"), HttpStatus.UNAUTHORIZED);
            }

            // Autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPassword());

            // Aquí, el `authenticationManager` se encarga de llamar al `UserDetailsService` para obtener un `UserDetails`
            // Esto lo gestiona internamente Spring Security
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Establecer el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT
            String token = jwtProvider.generateToken(authentication);

            // Obtener el usuario autenticado desde el contexto
            //UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Determinar el "username" correcto en la respuesta
            String responseUsername = user.getRole().getName().equals(RoleEnum.CIUDADANO) ? user.getUsername() : user.getEmail();

            // Preparar la respuesta con el token y los datos del usuario
            UserTokenDto userTokenDto = new UserTokenDto(token, responseUsername, user.getEmail(), user.getRole().getName(), user.getRole().getAllowedPlatforms());

            // Responder con los datos
            return new ResponseEntity<>(new Response<>(userTokenDto, false, 200, "Sesión iniciada correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            log.severe(e.getMessage());
            String message = "Algo salió mal";
            if (e instanceof RuntimeException) {
                message = e.getMessage();
            }
            return new ResponseEntity<>(new Response<>(null, true, 401, message), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para realizar el logout
    public Response<String> logout() {
        return new Response<>(null, false, 200, "Sesión cerrada exitosamente");
    }

    // Método para enviar correo de restablecimiento de contraseña con Thymeleaf
    public Response<Map<String, Object>> requestPasswordReset(PasswordResetRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isEmpty()) {
            //throw new RuntimeException("Usuario no encontrado con el correo proporcionado.");
            return new Response<>(null, true, 400, "Usuario no encontrado con el correo proporcionado.");
        }

        User user = userOptional.get();

        // Generar el token de restablecimiento
        String resetToken = jwtProvider.generatePasswordResetToken(user.getEmail());

        // Codificar el token antes de enviarlo
        String encodedToken = encodeToken(resetToken);

        // Enviar el correo de restablecimiento de contraseña usando Thymeleaf
        try {
            //sendPasswordResetEmail(user, resetToken);
            sendPasswordResetEmail(user, encodedToken);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de restablecimiento", e);
        }

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", resetToken);
        responseData.put("id", user.getUserId());
        responseData.put("firstName", user.getFirstName());

        return new Response<>(responseData, false, 200, "Correo enviado con éxito.");
    }

    // Método para enviar correo de restablecimiento usando Thymeleaf
    private void sendPasswordResetEmail(User user, String encodedToken) throws MessagingException {
        // Crear el contexto de Thymeleaf con los datos necesarios
        Map<String, Object> model = new HashMap<>();
        model.put("firstName", user.getFirstName()); // Agregar primer nombre del usuario

        // Construye la URL de restablecimiento de contraseña con el token
        String resetPasswordUrl = "http://127.0.0.1:9090/reset-password/" + encodedToken;  // Reemplaza con tu dominio
        model.put("resetPasswordUrl", resetPasswordUrl);

        // Renderizar la plantilla Thymeleaf
        String htmlContent = renderTemplate("reset-password", model);

        // Crear el mensaje de correo
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Restablecer Contraseña - SIMAC");
        helper.setText(htmlContent, true); // true indica que el contenido es HTML

        // Enviar el correo
        emailSender.send(message);
    }

    // Método para renderizar la plantilla Thymeleaf
    private String renderTemplate(String templateName, Map<String, Object> model) {
        try {
            // Crear el contexto para Thymeleaf
            Context thymeleafModel = new Context();
            thymeleafModel.setVariables(model); // Pasar las variables al contexto de Thymeleaf

            // Procesar la plantilla con el contexto
            return thymeleafViewResolver.getTemplateEngine().process(templateName, thymeleafModel);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la plantilla Thymeleaf", e);
        }
    }

    // Método para restablecer la contraseña
    public Response<String> resetPassword(String token, String newPassword) {
        String email = jwtProvider.getEmailFromPasswordResetToken(token);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new Response<>(null, true, 400, "Usuario no encontrado con el correo proporcionado.");
        }

        User user = userOptional.get();

        if (!jwtProvider.validatePasswordResetToken(token)) {
            return new Response<>(null, true, 400, "Token inválido o expirado.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return new Response<>(null, false, 200, "Contraseña actualizada y restablecida exitosamente.");
    }

    public boolean validateToken(String token) {
        // Verifica si el token es válido y si ya fue usado
        return jwtProvider.validateToken(token) && !usedTokens.contains(token);
    }

    public void markTokenAsUsed(String token) {
        usedTokens.add(token);
    }
}
