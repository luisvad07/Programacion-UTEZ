package utez.edu.mx.foodster.entities.categoriaspersonal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "categorias_personal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriasPersonal {

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id_categoria")
    private String idCategoria;

    @Column(name = "nombre", columnDefinition = "TEXT NOT NULL", nullable = false)
    private String nombre;

    @Column(name = "ultima_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ultimaModificacion;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

}
