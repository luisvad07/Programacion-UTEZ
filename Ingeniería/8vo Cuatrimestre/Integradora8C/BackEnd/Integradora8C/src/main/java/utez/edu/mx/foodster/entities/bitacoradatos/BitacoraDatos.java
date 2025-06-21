package utez.edu.mx.foodster.entities.bitacoradatos;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;


@Entity
@Table(name = "bitacora")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BitacoraDatos {
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(name = "id")
    private String id;
    @Column(columnDefinition = "json")
    @Type(JsonType.class)
    private JsonNode datos;

    @Column(name = "metodo", columnDefinition = "TEXT NOT NULL")
    private String metodo;

    @Column(name = "ruta_solicitada", columnDefinition = "TEXT NOT NULL")
    private String rutaSolicitada;

    @Column(name = "ip", columnDefinition = "TEXT NOT NULL")
    private String ip;

    @Column(name = "agente_usuario", columnDefinition = "TEXT NOT NULL")
    private String agenteUsuario;

    @Column(name = "estado_http", columnDefinition = "SMALLINT NOT NULL")
    private int estadoHttp;

    @Column(name = "creado_en", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp creadoEn;
}