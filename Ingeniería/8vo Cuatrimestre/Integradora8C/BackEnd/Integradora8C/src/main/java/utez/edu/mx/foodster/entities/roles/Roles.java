package utez.edu.mx.foodster.entities.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Roles {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_rol")
    private String idRol;

    @Column(name = "nombre", columnDefinition = "TEXT NOT NULL", nullable = false, unique = true)
    private String nombre;

    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    @JsonIgnore
    private Set<Usuarios> usuarios;

}
