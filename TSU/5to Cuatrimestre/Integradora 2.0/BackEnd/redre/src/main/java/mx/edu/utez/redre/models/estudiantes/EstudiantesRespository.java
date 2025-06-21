package mx.edu.utez.redre.models.estudiantes;

import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.models.responsables.Responsables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface EstudiantesRespository extends JpaRepository<Estudiantes, Long>{
    @Modifying
    @Query(value = "UPDATE asesor SET status = :status WHERE id = :id", nativeQuery = true)
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id")Long id);
    boolean existsByCorreo(String correo);

    boolean existsByMatricula(String matricula);

    List<Estudiantes> findAllByStatus(Boolean status);

    Estudiantes findByCorreo(String correo);

    Estudiantes getEstudiantesByNombre(String nombre);

    Estudiantes getEstudiantesByMatricula(String matricula);

    List<Estudiantes> findAllByAsesor(Asesor asesor);

    List<Estudiantes> findAllByAsesor_ResponsableAndReportStatus(Responsables responsables, String status);

    List<Estudiantes> findAllByAsesor_Responsable_DepartamentoAndReportStatus(Departamento departamento, String status);

    List<Estudiantes> findAllByAsesorAndReportStatus(Asesor asesor, String status);
}
