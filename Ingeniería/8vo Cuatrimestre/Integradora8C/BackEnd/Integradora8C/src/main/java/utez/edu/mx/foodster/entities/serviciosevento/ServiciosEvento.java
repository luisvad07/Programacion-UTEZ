package utez.edu.mx.foodster.entities.serviciosevento;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.eventos.Eventos;
import utez.edu.mx.foodster.entities.servicios.Servicios;

import java.sql.Timestamp;

@Entity
@Table(name = "servicios_evento")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiciosEvento {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_servicio_evento")
    private String idServicioEvento;
    @Column(name = "cantidad", columnDefinition = "BIGINT")
    private Long cantidad;
    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Eventos evento;
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicios servicio;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

}

