package com.school.citas.models.Solicitante;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Cita.Cita;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "solicitante")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Solicitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apePaterno", nullable = false)
    private String apePaterno;
    @Column(name = "apeMaterno") //No todos tienen dos apellidos
    private String apeMaterno;
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    @Column(name = "carrera", nullable = false)
    private String carrera;
    @Column(name = "correoElectronico", nullable = false, unique = true)
    private String correoElectronico;
    @Column(name = "telefono", nullable = false)
    private String telefono;
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
    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL)
    private List<Cita> citas;
}
