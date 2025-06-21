package com.school.citas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.school.citas.models.Horario.HorarioVentanilla;
import com.school.citas.models.Ventanilla.Ventanilla;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioDto {
    private Long id;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    //private LocalDateTime horarioInicio;
    //private LocalDateTime horarioFin;
   // private int cantidadRepeticiones;
    private Boolean status;
    private Ventanilla ventanilla;

    public HorarioVentanilla getHorario(){
        return new HorarioVentanilla(
                getId(),
                getDiaSemana(),
                getHoraInicio(),
                getHoraFin(),
                //getCantidadRepeticiones(),
                getStatus(),
                getVentanilla()
        );
    }
}
