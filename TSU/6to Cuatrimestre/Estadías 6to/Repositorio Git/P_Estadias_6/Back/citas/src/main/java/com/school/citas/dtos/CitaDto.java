package com.school.citas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import com.school.citas.models.Cita.Cita;
import com.school.citas.models.Servicio.Servicio;
import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.models.Ventanilla.Ventanilla;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CitaDto {

    private Long id;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private int numeroVentanilla;
    private String documentosAnexos;
    private double montoPago;
    private Boolean atendida;
    private Servicio servicio;
    private Ventanilla ventanilla;
    private Solicitante solicitante;

    public Cita getCita() {
        return new Cita(
                getId(),
                getFechaCita(),
                getHoraCita(),
                getNumeroVentanilla(),
                getDocumentosAnexos(),
                getMontoPago(),
                getAtendida(),
                getServicio(),
                getVentanilla(),
                getSolicitante()
        );
    }
}