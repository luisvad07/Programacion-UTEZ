package mx.edu.utez.redre.models.reportes.FilesMostrar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface FileRepository extends JpaRepository<FilesDtos, Long>{
    List<FilesDtos> findByCarrera(String carrera);
}
