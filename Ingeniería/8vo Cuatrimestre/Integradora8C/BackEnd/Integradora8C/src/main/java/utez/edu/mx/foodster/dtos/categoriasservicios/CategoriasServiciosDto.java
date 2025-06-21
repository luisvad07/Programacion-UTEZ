package utez.edu.mx.foodster.dtos.categoriasservicios;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServicios;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriasServiciosDto {

    private String idCategoria;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    private Timestamp ultimaModificacion;

    private Boolean active;

    public CategoriasServicios toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new CategoriasServicios(idCategoria, nombre, ultimaModificacion, active);
    }
}
