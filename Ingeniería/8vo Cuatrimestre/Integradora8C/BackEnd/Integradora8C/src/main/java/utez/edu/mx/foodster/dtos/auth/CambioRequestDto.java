package utez.edu.mx.foodster.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CambioRequestDto {
    @Email(message = "El correo debe ser válido")
    private String correo;
    @NotBlank(message = "La solucion del captcha no puede ser nula")
    private String solucion;
}

