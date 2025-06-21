package utez.edu.mx.foodster.entities.servicios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.categoriasservicios.CategoriasServicios;

import java.sql.Timestamp;

@Entity
@Table(name = "servicios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Servicios {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_servicio")
    private String idServicio;
    @Column(name = "nombre", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String descripcion;
    @Column(name = "precio", columnDefinition = "DOUBLE (10,2) NOT NULL", nullable = false)
    private Double precio;
    @Column(name = "precio_descuento", columnDefinition = "DOUBLE (10,2)")
    private Double precioDescuento;
    @Column(name = "imagen", columnDefinition = "MEDIUMTEXT NOT NULL", nullable = false)
    private String imagen;
    @Column(name = "existencias", columnDefinition = "BIGINT")
    private Long existencias;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriasServicios categoria;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
}
