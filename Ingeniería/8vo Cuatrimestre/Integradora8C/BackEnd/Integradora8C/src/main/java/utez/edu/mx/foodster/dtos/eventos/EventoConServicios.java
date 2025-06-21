package utez.edu.mx.foodster.dtos.eventos;

import jakarta.validation.Valid;
import lombok.*;
import utez.edu.mx.foodster.dtos.serviciosevento.ServiciosParaEventoDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EventoConServicios {
    @Valid
    private EventosDto evento;
    @Valid
    private List<ServiciosParaEventoDto> servicios;
    private String idPaquete;
}
