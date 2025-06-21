package utez.edu.mx.foodster.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.foodster.dtos.auth.CambioRequestDto;
import utez.edu.mx.foodster.dtos.auth.CambioResponseDto;
import utez.edu.mx.foodster.dtos.auth.UsuarioTokenDto;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.entities.personal.PersonalRepository;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.security.jwt.JwtProvider;
import utez.edu.mx.foodster.services.captcha.CaptchaService;
import utez.edu.mx.foodster.services.mailservice.MailService;
import utez.edu.mx.foodster.services.twilio.TwilioServices;
import utez.edu.mx.foodster.services.usuarios.UsuariosServices;
import utez.edu.mx.foodster.utils.HtmlMessageRender;
import utez.edu.mx.foodster.utils.Response;

import java.util.logging.Logger;

@Service
@Transactional
public class AuthService {

    private final Logger logger = Logger.getLogger(AuthService.class.getName());

    private final UsuariosServices service;


    private final PersonalRepository personalRepository;

    private final AuthenticationManager manager;

    private final JwtProvider provider;

    private final MailService emailService;

    private final TwilioServices twilioService;
    private final HtmlMessageRender htmlRender;
    private final CaptchaService captchaService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UsuariosServices service, PersonalRepository personalRepository, AuthenticationManager manager, JwtProvider provider, MailService emailService, TwilioServices twilioService, HtmlMessageRender htmlRender, CaptchaService captchaService, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.personalRepository = personalRepository;
        this.manager = manager;
        this.provider = provider;
        this.emailService = emailService;
        this.twilioService = twilioService;
        this.htmlRender = htmlRender;
        this.captchaService = captchaService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Response<UsuarioTokenDto>> login(String username, String password) {
        try {
            Usuarios foundUser = service.getByCorreo(username);
            if (foundUser == null)
                return new ResponseEntity<>(new Response<>(null, true, 404, "Usuario no encontrado para login"), HttpStatus.NOT_FOUND);
            if (Boolean.FALSE.equals(foundUser.getActive())) {
                return new ResponseEntity<>(new Response<>(null, true, 401, "Usuario inactivo"), HttpStatus.UNAUTHORIZED);
            }
            Personal personal = personalRepository.findByIdUsuarioAndActive(foundUser.getIdUsuario(), true);
            Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);
            UsuarioTokenDto data = new UsuarioTokenDto(foundUser, token, personal);
            return new ResponseEntity<>(new Response<>(data, false, 200, "OK"), HttpStatus.OK);
        } catch (Exception e) {
            this.logger.severe(e.getMessage());
            String message = "Credenciales incorrectas";
            if (e instanceof DisabledException) message = "Usuario inactivo";
            return new ResponseEntity<>(new Response<>(null, true, 401, message), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response<CambioResponseDto>> resetPassword(CambioRequestDto dto) {
        Boolean captchaVerification = this.captchaService.verificarCaptchaBoolean(dto.getSolucion());
        if (captchaVerification == null || !captchaVerification) {
            return new ResponseEntity<>(new Response<>(null, true, 400, "Captcha invalido"), HttpStatus.BAD_REQUEST);
        }
        String correo = dto.getCorreo();
        Usuarios user = service.getByCorreo(correo);
        if (user == null) {
            return new ResponseEntity<>(new Response<>(null, true, 404, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
        String token = provider.generatePasswordResetToken(correo);
        String url = "http://localhost:5173/restablecer?token=" + token;
        String message = htmlRender.renderRecover(user.getNombres(), url);
        try {
            emailService.sendHtmlMessage(correo, "Recuperar contraseña", message);
            twilioService.sendSMS(user.getTelefono(), "Para recuperar tu contraseña, visita " + url);
            return new ResponseEntity<>(new Response<>(new CambioResponseDto(correo), false, 200, "Correo enviado"), HttpStatus.OK);
        } catch (Exception e) {
            this.logger.severe(e.getMessage());
            return new ResponseEntity<>(new Response<>(null, true, 500, "Error al enviar correo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Response<CambioResponseDto>> confirmResetPassword(String token, String password) {
        String correo = provider.getEmailFromPasswordResetToken(token);
        Usuarios user = service.getByCorreo(correo);
        if (user == null) {
            return new ResponseEntity<>(new Response<>(null, true, 404, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
        user.setContrasena(passwordEncoder.encode(password));
        service.insert(user);
        return new ResponseEntity<>(new Response<>(new CambioResponseDto(correo), false, 200, "Contraseña actualizada"), HttpStatus.OK);
    }

}
