package utez.edu.mx.foodster.dtos.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.validation.FieldMatch;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldMatch(first = "nuevaContrasena", second = "confirmarContrasena", message = "Las contraseñas deben coincidir")
public class CambiarContraDto {
    @NotBlank(message = "El correo no puede ser nulo")
    @Email(message = "El correo debe ser válido")
    private String correo;
    @NotBlank(message = "La contraseña no puede ser nula")
    private String contrasena;
    @NotBlank(message = "La nueva contraseña no puede ser nula")
    private String nuevaContrasena;
    @NotBlank(message = "La confirmación de la contraseña no puede ser nula")
    private String confirmarContrasena;
}
