package utez.edu.mx.foodster.dtos.calificaciones;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import utez.edu.mx.foodster.entities.calificaciones.Calificaciones;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalificacionesDto {
    private String idCalificacion;


    private Servicios servicios;

    private Paquetes paquetes;

    @Min(value = 1, message = "La calificacion no puede ser menor a 1")
    @Max(value = 5, message = "La calificacion no puede ser mayor a 5")
    private int calificacion;

    @NotNull(message = "El usuario es obligatorio")
    private Usuarios usuario;

    @Size(max = 500, message = "El comentario no puede tener mas de 500 caracteres")
    private String comentario;

    private Timestamp ultimaModificacion;

    @NotNull(message = "El campo active no puede ser nulo")
    private Boolean active;

    public Calificaciones toEntity(){
        this.ultimaModificacion = new java.sql.Timestamp(System.currentTimeMillis());
        return new Calificaciones(idCalificacion, servicios, usuario, paquetes, calificacion, comentario, ultimaModificacion, active);
    }
}