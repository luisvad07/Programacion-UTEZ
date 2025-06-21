package utez.edu.mx.foodster.entities.direcciones;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "direcciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Direcciones {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_direccion")
    private String idDireccion;

    @Column(name = "calle", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String calle;

    @Column(name = "colonia", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String colonia;

    @Column(name = "numero", columnDefinition = "TEXT")
    private String numero;

    @Column(name = "codigo_postal", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String codigoPostal;

    @Column(name = "municipio", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String municipio;

    @Column(name = "estado", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String estado;

    @Column(name = "referencias", columnDefinition = "TEXT")
    private String referencias;

    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;

    @Column( columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
}
