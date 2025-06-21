package utez.edu.mx.foodster.dtos.serviciosevento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiciosParaEventoDto {
    private Long cantidad;
    @NotNull(message = "El servicio no puede ser nulo")
    @NotBlank(message = "El servicio no puede ser nulo")
    private String idServicio;
}