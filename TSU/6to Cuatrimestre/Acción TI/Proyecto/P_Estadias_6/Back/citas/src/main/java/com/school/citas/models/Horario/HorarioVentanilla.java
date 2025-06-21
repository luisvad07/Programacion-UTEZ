package com.school.citas.models.Horario;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.school.citas.models.Ventanilla.Ventanilla;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horarios")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HorarioVentanilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diaSemana", nullable = false)
    private String diaSemana;
    /*@Column(name = "horaInicio", nullable = false)
    private String horaInicio;
    @Column(name = "horaFin", nullable = false)
    private String horaFin;*/
    /* Funcionan
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "horarioInicio", columnDefinition = "datetime", nullable = false)
    private LocalDateTime horarioInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "horarioFin", columnDefinition = "datetime", nullable = false)
    private LocalDateTime horarioFin;*/

    /* Sólo horas*/
    @Column(name = "horaInicio", columnDefinition = "time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;

    @Column(name = "horaFin", columnDefinition = "time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;

    /*@Column(name = "cantidadRepeticiones", nullable = false)
    private int cantidadRepeticiones;*/

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    /*@ManyToOne
    private Ventanilla ventanilla;*/

    @ManyToOne
    @JoinColumn(name = "ventanilla_id", nullable = false)
    @JsonBackReference
    private Ventanilla ventanilla;
}
