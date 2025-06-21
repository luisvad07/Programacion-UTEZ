package mx.edu.utez.redre.models.asesor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.models.responsables.Responsables;
//import mx.edu.utez.redre.models.usuario.Usuario;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
@Table(name = "asesores")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Asesor {
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
    @JoinColumn(name = "responsable_id", nullable = false)
    @JsonBackReference
    private Responsables responsable;

    @OneToMany(mappedBy = "asesor")
    @JsonManagedReference
    private List<Estudiantes> estudiantes;

    /* 
    @OneToOne(mappedBy = "asesor",
            cascade = CascadeType.ALL,
            optional = false)
    private Usuario user;*/
}
