package utez.edu.mx.foodster.dtos.serviciospaquete;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.entities.servicios.Servicios;
import utez.edu.mx.foodster.entities.serviciospaquete.ServiciosPaquete;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiciosPaqueteDto {
    private String idServicioPaquete;
    @NotNull(message = "El paquete no puede ser nulo")
    private Paquetes paquete;
    @NotNull(message = "El servicio no puede ser nulo")
    private Servicios servicio;
    private Timestamp ultimaModificacion;
    private Boolean active;

    public ServiciosPaquete toEntity(){
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new ServiciosPaquete(idServicioPaquete, paquete, servicio, ultimaModificacion, active);
    }
}