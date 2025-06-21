package mx.edu.utez.redre.models.mensajes.models.mensajeAsesor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "mensajes_asesor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MensajeAsesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mensaje")
    private String mensaje;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    //@PastOrPresent
    @Column(name = "fecha_envio", nullable = false, columnDefinition = "datetime")
    private LocalDateTime fechaEnvio;
    @Column(name = "aceptacion", nullable = false, columnDefinition = "tinyint default 1")
    private Boolean status;
    @Column(name = "estudiantes_matricula", nullable = false)
    private String nombre;
    @Column(name = "asesor", nullable = false)
    private String nombreA;
}
