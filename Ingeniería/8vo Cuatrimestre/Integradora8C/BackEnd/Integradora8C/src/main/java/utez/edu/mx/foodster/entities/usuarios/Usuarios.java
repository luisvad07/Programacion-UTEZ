package utez.edu.mx.foodster.entities.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.roles.Roles;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuarios {

    @Id
    @UuidGenerator
    @Column(name = "id_usuario")
    private String idUsuario;
    @Column(name = "nombres", columnDefinition = "TEXT NOT NULL")
    private String nombres;
    @Column(name = "primer_apellido", columnDefinition = "TEXT NOT NULL")
    private String primerApellido;
    @Column(name = "segundo_apellido", columnDefinition = "TEXT NOT NULL")
    private String segundoApellido;
    @Column(name = "telefono", columnDefinition = "TEXT NOT NULL")
    private String telefono;
    @Column(name = "correo", columnDefinition = "TEXT NOT NULL")
    private String correo;
    @Column(name = "contrasena", columnDefinition = "TEXT NOT NULL")
    private String contrasena;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
    @ManyToMany
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Roles> roles = new HashSet<>();

}
