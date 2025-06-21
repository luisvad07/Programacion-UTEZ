package utez.edu.mx.foodster.entities.eventos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;

@Entity
@Table(name = "eventos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Eventos {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_evento")
    private String idEvento;
    @Column(name = "fecha_hora_inicio", columnDefinition = "TIMESTAMP NOT NULL")
    private Timestamp fechaHoraInicio;
    @Column(name = "fecha_hora_fin", columnDefinition = "TIMESTAMP NOT NULL")
    private Timestamp fechaHoraFin;
    @Column(name = "numero_personas", columnDefinition = "BIGINT NOT NULL")
    private Long numeroPersonas;
    @Column(name = "costo_total", columnDefinition = "DOUBLE (10,2) NOT NULL")
    private Double costoTotal;
    @Column(name = "personalizado", columnDefinition = "BOOLEAN NOT NULL")
    private Boolean personalizado;
    @Column(name = "estado", columnDefinition = "TEXT NOT NULL")
    private String estado;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios usuario;
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    @ManyToOne
    private Direcciones direccion;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
}
