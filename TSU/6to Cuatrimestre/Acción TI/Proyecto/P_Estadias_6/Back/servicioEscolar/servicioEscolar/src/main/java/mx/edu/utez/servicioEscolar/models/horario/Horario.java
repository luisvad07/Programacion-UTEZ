package mx.edu.utez.servicioEscolar.models.horario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "diaSemana", nullable = false)
    private String diaSemana;
    @Column(name = "horarioInicio", nullable = false)
    private LocalTime horarioInicio;
    @Column(name = "horarioFin", nullable = false)
    private LocalTime horarioFin;
    @Column(name = "cantidadRepeticiones", nullable = false)
    private int cantidadRepeticiones;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @ManyToOne
    private Ventanilla ventanilla;

}
