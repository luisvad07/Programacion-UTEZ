package mx.edu.utez.servicioEscolar.models.cita;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.servicio.Servicio;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fechaCita", nullable = false)
    private LocalDateTime fechaCita;
    @Column(name = "horaCita", nullable = false)
    private LocalTime horaCita;
    @Column(name = "montoPago", nullable = false)
    private double montoPago;
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @ManyToOne
    private Ventanilla ventanilla;

    @ManyToOne
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private Solicitante solicitante;

}
