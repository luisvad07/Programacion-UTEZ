package com.school.citas.models.Cita;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.*;
import com.school.citas.models.Servicio.Servicio;
import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.models.Ventanilla.Ventanilla;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cita")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    /*@Column(name = "fecha", nullable = false)
    private LocalDate fecha;*/
    @Column(name = "hora", columnDefinition = "time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime hora;
    @Column(name = "numeroVentanilla", nullable = false)
    private int numeroVentanilla;
    @Column(name = "documentosAnexos", nullable = false)
    private String documentosAnexos;
    @Column(name = "montoPago", nullable = false)
    private double montoPago;
    //@Column(name = "atendida", nullable = false, columnDefinition = "tinyint default 1")
    @Column(name = "atendida", nullable = false, columnDefinition = "tinyint default 0")
    private boolean atendida;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    
    @ManyToOne
    @JoinColumn(name = "ventanilla_id")
    private Ventanilla ventanilla;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Solicitante solicitante;
}
