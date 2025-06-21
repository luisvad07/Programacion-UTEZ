package utez.edu.mx.foodster.entities.serviciospaquete;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.paquetes.Paquetes;
import utez.edu.mx.foodster.entities.servicios.Servicios;

import java.sql.Timestamp;

@Entity
@Table(name = "servicios_paquete")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiciosPaquete {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_servicio_paquete")
    private String idServicioPaquete;
    @ManyToOne
    @JoinColumn(name = "id_paquete")
    private Paquetes paquete;
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicios servicio;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;


}
