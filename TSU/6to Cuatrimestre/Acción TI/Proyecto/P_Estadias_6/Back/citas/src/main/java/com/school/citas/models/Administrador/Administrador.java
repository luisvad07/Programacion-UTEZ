package com.school.citas.models.Administrador;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.school.citas.models.Servicio.Servicio;
import com.school.citas.models.Solicitante.Solicitante;
import com.school.citas.models.Ventanilla.Ventanilla;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administrador")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreAdmin", nullable = false)
    private String nombreAdmin;
    @Column(name = "apePaternoAdmin", nullable = false)
    private String apePaternoAdmin;
    @Column(name = "apeMaternoAdmin") //No todos tienen dos apellidos
    private String apeMaternoAdmin;
    @Column(name = "correoAdmin", nullable = false, unique = true)
    private String correoAdmin;
    @Column(name = "pass", nullable = false)
    private String pass;
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1")
    private boolean status;
    @Column(name = "changePassword", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean changePassword;

    @OneToMany(mappedBy = "admin")
    private List<Ventanilla> ventanillas;
    @OneToMany(mappedBy = "admin")
    private List<Solicitante> solicitantes;
    @OneToMany(mappedBy = "admin")
    private List<Servicio> servicios;

}
