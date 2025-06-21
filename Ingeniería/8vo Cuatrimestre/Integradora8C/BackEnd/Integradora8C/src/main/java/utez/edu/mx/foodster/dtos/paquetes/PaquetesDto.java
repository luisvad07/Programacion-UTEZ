package utez.edu.mx.foodster.dtos.paquetes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;

import java.sql.Timestamp;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PaquetesDto {

    private String idPaquete;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción no puede ser nula")
    @Size(min = 5, max = 70, message = "La descripción debe tener entre 5 y 70 caracteres")
    private String descripcion;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String recomendadoPara;

    private String imagen;

    private long numeroPedidos;

    private Timestamp ultimaModificacion;

    private Boolean active;

    public Paquetes toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Paquetes(idPaquete, nombre, descripcion, recomendadoPara, imagen, numeroPedidos, ultimaModificacion, active);
    }
}
