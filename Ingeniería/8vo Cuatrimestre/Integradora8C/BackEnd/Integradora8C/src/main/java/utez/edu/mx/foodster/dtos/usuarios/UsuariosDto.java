package utez.edu.mx.foodster.dtos.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.roles.Roles;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuariosDto {

    private String idUsuario;
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
    private Timestamp ultimaModificacion;
    private Boolean active;
    private Set<Roles> roles;

    public Usuarios toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Usuarios(idUsuario, nombres, primerApellido, segundoApellido, telefono, correo, contrasena, ultimaModificacion, active, roles);
    }
}
