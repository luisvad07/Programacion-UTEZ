package mx.edu.utez.redre.models.responsables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.models.departamento.Departamento;
//import mx.edu.utez.redre.models.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "responsables")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Responsables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;
    @Column(name = "division_academica", nullable = false)
    private String divisionAcademica;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    @JsonBackReference
    private Departamento departamento;

    @OneToMany(mappedBy = "responsable")
    @JsonManagedReference
    private List<Asesor> asesores;

    /* 
    @OneToOne(mappedBy = "responsable",
            cascade = CascadeType.ALL,
            optional = false)
    private Usuario user;*/
}
