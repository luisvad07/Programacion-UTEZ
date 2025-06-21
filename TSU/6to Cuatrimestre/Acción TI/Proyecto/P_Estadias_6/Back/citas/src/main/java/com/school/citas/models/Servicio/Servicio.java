package com.school.citas.models.Servicio;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicio")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomserv", nullable = false)
    private String nomserv;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "documentos", nullable = false)
    private String documentos;
    @Column(name = "costo", nullable = false)
    private double costo;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Administrador admin;

    @OneToMany(mappedBy = "servicio")
    private List<Cita> citas;

}
