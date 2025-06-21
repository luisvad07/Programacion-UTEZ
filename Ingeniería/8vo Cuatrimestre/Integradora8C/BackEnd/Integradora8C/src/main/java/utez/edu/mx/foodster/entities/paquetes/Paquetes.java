package utez.edu.mx.foodster.entities.paquetes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "paquetes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paquetes {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_paquete")
    private String idPaquete;
    @Column(name = "nombre", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String descripcion;
    @Column(name = "recomendado_para", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String recomendadoPara;
    @Column(name = "imagen", columnDefinition = "MEDIUMTEXT NOT NULL", nullable = false)
    private String imagen;
    @Column(name = "numero_pedidos", columnDefinition = "BIGINT")
    private long numeroPedidos;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
}
