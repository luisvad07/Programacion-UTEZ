package utez.edu.mx.foodster.dtos.direcciones;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DireccionesDto {

    private String idDireccion;

    @NotBlank(message = "La calle no puede ser nula")
    @Size(max = 100, message = "calle must be maximum 100 characters")
    private String calle;

    @NotBlank(message = "La colonia no puede ser nula")
    @Size(max = 100, message = "La colonia debe tener maximo 100 caracteres")
    private String colonia;

    @Size(max = 3, message = "El numero debe tener maximo 3 caracteres")
    private String numero;

    @NotBlank(message = "El codigo postal no puede ser nulo")
    @Size(max = 5, message = "El codigo postal debe tener maximo 5 caracteres")
    private String codigoPostal;

    @NotBlank(message = "El municipio no puede ser nulo")
    @Size(max = 100, message = "El municipio debe tener maximo 100 caracteres")
    private String municipio;

    @NotBlank(message = "El estado no puede ser nulo")
    @Size(max = 100, message = "El estado debe tener maximo 100 caracteres")
    private String estado;

    @Size(max = 200, message = "Las referencias deben tener maximo 200 caracteres")
    private String referencias;

    private Timestamp ultimaModificacion;

    private Boolean active;


    private String idUsuario;



    public Direcciones toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Direcciones(idDireccion, calle, colonia, numero, codigoPostal, municipio, estado, referencias, ultimaModificacion, active);
    }
}
