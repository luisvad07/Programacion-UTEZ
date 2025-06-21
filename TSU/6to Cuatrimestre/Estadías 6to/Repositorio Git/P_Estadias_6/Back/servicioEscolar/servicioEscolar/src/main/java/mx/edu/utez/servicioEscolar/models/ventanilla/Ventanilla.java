package mx.edu.utez.servicioEscolar.models.ventanilla;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.cita.Cita;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ventanillas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

public class Ventanilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreVent", nullable = false)
    private String nombre;
    @Column(name = "apeMaternoVent", nullable = false)
    private String apeMaterno;
    @Column(name = "apePaternoVent") //No todos tienen dos apellidos
    private String apePaterno;
    @Column(name = "correoVent", nullable = false, unique = true)
    private String correoVent;
    @Column(name = "passwordVent", nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @OneToMany(mappedBy = "ventanilla")
    private List<Cita> citas;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Admin admin;
}
