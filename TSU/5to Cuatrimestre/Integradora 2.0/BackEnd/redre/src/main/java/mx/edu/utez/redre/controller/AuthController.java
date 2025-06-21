/*package mx.edu.utez.redre.controller;

import mx.edu.utez.redre.models.usuario.Usuario;
import mx.edu.utez.redre.security.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redre/auth")
public class AuthController {
    @Autowired
    private UserServices userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        if (userService.autenticar(usuario)) {
            return ResponseEntity.ok().body("Ingreso Correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas o rol de usuario no válido");
        }
    }
}*/