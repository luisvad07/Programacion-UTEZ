package utez.edu.mx.foodster.dtos.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UsuariosPublicDto {
    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombres;
    @NotBlank(message = "El apellido paterno no puede ser nulo")
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String primerApellido;

    @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
    private String segundoApellido;

    @NotBlank(message = "El numero de telefono no puede ser nulo")
    @Size(min = 10, max = 10, message = "El numero de telefono debe tener 10 caracteres")
    private String telefono;

    @NotBlank(message = "El correo no puede ser nulo")
    @Size(min = 3, max = 255, message = "El correo debe tener entre 3 y 255 caracteres")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede ser nula")
    @Size(min = 3, max = 255, message = "La contraseña debe tener entre 3 y 255 caracteres")
    private String contrasena;
    @NotBlank(message = "La solucion del captcha no puede ser nula")
    private String solucion;

    public Usuarios toEntity() {
        Usuarios usuario = new Usuarios();
        usuario.setNombres(nombres);
        usuario.setPrimerApellido(primerApellido);
        usuario.setSegundoApellido(segundoApellido);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setUltimaModificacion(new Timestamp(System.currentTimeMillis()));
        return usuario;
    }
}
