package utez.edu.mx.foodster.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioTokenDto {
    @NotNull
    private Usuarios usuarios;

    @NotBlank
    private String token;

    private Personal personal;

}
