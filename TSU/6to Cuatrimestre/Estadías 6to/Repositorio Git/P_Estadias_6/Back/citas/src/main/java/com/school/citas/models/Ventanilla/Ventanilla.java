package com.school.citas.models.Ventanilla;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Cita.Cita;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.school.citas.models.Horario.HorarioVentanilla;
import com.school.citas.models.Servicio.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ventanilla")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ventanilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreVent", nullable = false)
    private String nombreVent;
    @Column(name = "apePaternoVent", nullable = false)
    private String apePaternoVent;
    @Column(name = "apeMaternoVent") //No todos tienen dos apellidos
    private String apeMaternoVent;
    @Column(name = "correoElectronico", nullable = false, unique = true)
    private String correoElectronico;
    @Column(name = "pass", nullable = false)
    private String pass;
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1")
    private boolean status;
    @Column(name = "changePassword", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean changePassword;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Administrador admin;

    @OneToMany(mappedBy = "ventanilla")
    private List<HorarioVentanilla> horarioventanilla;

    @OneToMany(mappedBy = "ventanilla")
    private List<Cita> citas;


}