package mx.edu.utez.servicioEscolar.models.solicitante;


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
@Table(name = "solicitantes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apeMaterno", nullable = false)
    private String apeMaterno;
    @Column(name = "apePaterno", nullable = false)
    private String apePaterno;
    @Column(name = "correoSoli", nullable = false, unique = true)
    private String correoSoli;
    @Column(name = "passwordSoli", nullable = false)
    private String passwordSoli;
    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    private Admin admin;

    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL)
    private List<Cita> citas;
}
