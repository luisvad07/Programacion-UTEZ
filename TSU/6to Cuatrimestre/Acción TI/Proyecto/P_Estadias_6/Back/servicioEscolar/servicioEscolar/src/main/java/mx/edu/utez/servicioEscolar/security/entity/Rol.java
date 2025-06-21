package mx.edu.utez.servicioEscolar.security.entity;

import mx.edu.utez.servicioEscolar.security.enums.RolNombre;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolNombre rolNombre;

    // Getter y Setter
}
