package mx.edu.utez.redre.models.reportesFinales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportesFinalesRepository extends JpaRepository<ReportesFinales, Long> {
    ReportesFinales findByMatricula(String matricula);

    List<ReportesFinales> getAllByDivisionAcademica(String division);

    boolean existsByMatricula(String matricula);

    List<ReportesFinales> getAllByDivisionAcademicaAndUrlReporteINGNotNull(String division);
}
