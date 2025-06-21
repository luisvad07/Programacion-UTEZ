package mx.edu.utez.redre.models.reportes.FilesMostrar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "registros_movil")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class FilesDtos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
	@NotBlank
    private String nombreArchivo;
    @Column(name = "tipo", nullable = false)
	@NotBlank
    private String tipo;
    @Column(name = "carrera", nullable = false)
	@NotBlank
	private String carrera;
    @Column(name = "url", nullable = false)
	private String url;
}
