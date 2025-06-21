package utez.edu.mx.foodster.controllers.auth;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.foodster.dtos.auth.*;
import utez.edu.mx.foodster.services.auth.AuthService;
import utez.edu.mx.foodster.utils.Response;

@RestController
@RequestMapping("${apiPrefix}/auth")
@CrossOrigin(value = {"*"})
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Response<UsuarioTokenDto>> login(@RequestBody @Valid SignDto dto) {
        return service.login(dto.getCorreo(), dto.getContrasenia());
    }

    @PostMapping("/restablecer")
    public ResponseEntity<Response<CambioResponseDto>> restablecerContrasenia(@RequestBody @Valid CambioRequestDto dto) {
        return service.resetPassword(dto);
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Response<CambioResponseDto>> confirmarCambio(@RequestBody @Valid RestablecerContraDto dto) {
        return service.confirmResetPassword(dto.getToken(), dto.getContrasenia());
    }
}
