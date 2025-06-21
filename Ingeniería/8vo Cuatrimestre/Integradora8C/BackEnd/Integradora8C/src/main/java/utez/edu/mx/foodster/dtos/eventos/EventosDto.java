package utez.edu.mx.foodster.dtos.eventos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.validation.TimeAfter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TimeAfter(start = "fechaHoraInicio", end = "fechaHoraFin", message = "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio")
public class EventosDto {

    private String idEvento;
    @NotNull(message = "La fecha y hora de inicio no pueden ser nulas")
    private Timestamp fechaHoraInicio;
    @NotNull(message = "La fecha y hora de fin no pueden ser nulas")
    private Timestamp fechaHoraFin;

    @NotNull(message = "El número de personas no puede ser nulo")
    @Min(value = 1, message = "El número de personas debe ser mayor a 0")
    @Max(value = 1000, message = "El número de personas debe ser menor a 100")
    private Long numeroPersonas;

    @NotNull(message = "La dirección no puede ser nula")
    private Direcciones direccion;

    @NotNull(message = "El usuario no puede ser nulo")
    private Usuarios usuario;

    private Double costoTotal;

    private Boolean personalizado;

    private String estado;

    private Timestamp ultimaModificacion;

    private Boolean active;

    public Eventos toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Eventos(idEvento, fechaHoraInicio, fechaHoraFin, numeroPersonas, costoTotal, personalizado, estado, usuario, direccion, ultimaModificacion, active);
    }
}