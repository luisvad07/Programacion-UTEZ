package mx.edu.utez.servicioEscolar.models.servicio;

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
@Table(name = "servicios")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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
    private int costo;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @OneToMany(mappedBy = "servicio")
    private List<Cita> citas;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Admin admin;
}
