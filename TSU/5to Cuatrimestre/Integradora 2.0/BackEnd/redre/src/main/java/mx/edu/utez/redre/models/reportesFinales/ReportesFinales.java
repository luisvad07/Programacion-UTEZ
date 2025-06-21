package mx.edu.utez.redre.models.reportesFinales;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "reportes_finales")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class ReportesFinales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    @Column(name = "division_academica", nullable = false)
    private String divisionAcademica;
    @Column(name = "url_reporte_ing")
    private String urlReporteING;
    @Column(name = "url_reporte_tsu", nullable = false)
    private String urlReporteTSU;
}
