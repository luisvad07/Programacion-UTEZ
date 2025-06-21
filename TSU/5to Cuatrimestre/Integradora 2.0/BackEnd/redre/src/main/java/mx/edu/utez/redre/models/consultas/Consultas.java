package mx.edu.utez.redre.models.consultas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Consultas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "fecha_consulta", columnDefinition = "datetime", nullable = false)
    private LocalDateTime fechaConsulta;
    @Column(name = "usuario", nullable = false)
	private String usuario;
    @Column(name = "url", nullable = false)
    private String url;
    
}
