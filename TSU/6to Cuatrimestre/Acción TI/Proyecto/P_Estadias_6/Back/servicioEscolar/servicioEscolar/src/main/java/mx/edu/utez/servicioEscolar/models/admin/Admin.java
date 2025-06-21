package mx.edu.utez.servicioEscolar.models.admin;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.horario.Horario;
import mx.edu.utez.servicioEscolar.models.servicio.Servicio;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admins")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Admin {

    /*Nombre completo
● Número de empleado
● Correo electrónico
*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreAdmin", nullable = false)
    private String nombreAdmin;
    @Column(name = "apeMaternoAdmin", nullable = false)
    private String apeMaternoAdmin;
    @Column(name = "apePaternoAdmin") //No todos tienen dos apellidos
    private String apePaternoAdmin;
    @Column(name = "correoAdmin", nullable = false, unique = true)
    private String correoAdmin;
    @Column(name = "passwordAdmin", nullable = false)
    private String passwordAdmin;
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;

    ///Gestiona Ventanillas
    @OneToMany(mappedBy = "admin")
    private List<Ventanilla> ventanillas;

    ///Gestiona solicitantes
    @OneToMany(mappedBy = "admin")
    private List<Solicitante> solicitantes;

    ///Gestiona servicios
    @OneToMany(mappedBy = "admin")
    private List<Servicio> servicios;

}
