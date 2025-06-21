package utez.edu.mx.foodster.entities.tarjetas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "Tarjetas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarjetas {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_tarjeta")
    private String idTarjeta;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios usuario;
    @Column(name = "numero_tarjeta", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String numeroTarjeta;
    @Column(name = "nombre_tarjeta", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String nombreTarjeta;
    @Column(name = "fecha_vencimiento", columnDefinition = "DATE NOT NULL", nullable = false)
    private Date fechaVencimiento;
    @Column(name = "cvv", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String cvv;
    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;
}