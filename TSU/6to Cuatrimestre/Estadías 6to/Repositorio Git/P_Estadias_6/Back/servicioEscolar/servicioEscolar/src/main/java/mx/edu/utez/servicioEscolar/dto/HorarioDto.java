package mx.edu.utez.servicioEscolar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.horario.Horario;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioDto {
    private Long id;
    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private int cantidadRepeticiones;
    private Boolean status;
    private Ventanilla ventanilla;

    public Horario getHorario(){
        return new Horario(
                getId(),
                getDiaSemana(),
                getHorarioInicio(),
                getHorarioFin(),
                getCantidadRepeticiones(),
                getStatus(),
                getVentanilla()
        );
    }
}
