package mx.edu.utez.servicioEscolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.cita.Cita;
import mx.edu.utez.servicioEscolar.models.servicio.Servicio;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CitaDto {

    private Long id;
    private LocalDateTime fechaCita;
    private LocalTime horaCita;
    private double montoPago;
    private Boolean status;
    private Ventanilla ventanilla;
    private Servicio servicio;
    private Solicitante solicitante;

    public Cita getCita() {
        return new Cita(
                getId(),
                getFechaCita(),
                getHoraCita(),
                getMontoPago(),
                getStatus(),
                getVentanilla(),
                getServicio(),
                getSolicitante()
        );
    }
}
