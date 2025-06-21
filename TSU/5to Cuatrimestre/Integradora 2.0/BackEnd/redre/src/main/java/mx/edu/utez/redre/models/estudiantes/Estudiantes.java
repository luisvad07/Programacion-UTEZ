package mx.edu.utez.redre.models.estudiantes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.asesor.Asesor;

//import mx.edu.utez.redre.models.usuario.Usuario;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "estudiantes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Estudiantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "division_academica", nullable = false)
    private String divisionAcademica;
    @Column(name = "carrera", nullable = false)
    private String carrera;
    @Column(name = "grado", nullable = false)
    private int grado;
    @Column(name = "grupo", nullable = false)
    private char grupo;
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;
    @Column(name = "url_reporte")
    private String urlReporte;
    @Column(name = "resport_status")
    private String reportStatus;

    @ManyToOne
    @JoinColumn(name = "asesor_id", nullable = false)
    @JsonBackReference
    private Asesor asesor;

    /*
    @OneToOne(mappedBy = "estudiantes",
            cascade = CascadeType.ALL,
            optional = false)
    private Usuario user; */
}
