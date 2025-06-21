package mx.edu.utez.redre.models.mensajes.models.mensajeResponsable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "mensajes_responsables")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MensajeResponsable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mensaje")
    private String mensaje;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "fecha_envio", nullable = false, columnDefinition = "datetime")
    private LocalDateTime fechaEnvio;
    @Column(name = "aceptacion", nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;
    @Column(name = "asesor", nullable = false)
    private String nombre;
    @Column(name = "responsable", nullable = false)
    private String nombreR;
}
