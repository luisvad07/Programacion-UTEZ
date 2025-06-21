package utez.edu.mx.foodster.dtos.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestablecerContraDto {
    @NotBlank
    private String token;
    @NotBlank
    private String contrasenia;
}

