package utez.edu.mx.foodster.dtos.serviciosevento;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.entities.serviciosevento.ServiciosEvento;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiciosEventoDto {
    private String idServicioEvento;
    private Long cantidad;
    @NotNull(message = "El evento no puede ser nulo")
    private Eventos evento;
    @NotNull(message = "El servicio no puede ser nulo")
    private Servicios servicio;
    private Timestamp ultimaModificacion;
    private Boolean active;

    public ServiciosEvento toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new ServiciosEvento(idServicioEvento, cantidad, evento, servicio, ultimaModificacion, active);
    }
}