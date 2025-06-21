package utez.edu.mx.foodster.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignDto {
    @NotBlank
    @NotEmpty
    @Email(message = "El correo debe ser válido")
    private String correo;
    @NotBlank
    @NotEmpty

    private String contrasenia;
}
