package mx.edu.utez.servicioEscolar.models.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

    @Modifying
    @Query(
        value = "UPDATE admins SET status = :status WHERE id = :id",
        nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    @Override
    Optional<Admin> findById(Long aLong);

    boolean existsByCorreoAdmin(String correoAdmin);

    List<Admin> findAllByStatus(Boolean status);
    ///finDA
    Admin findByCorreoAdmin(String correoAdmin);
    Admin getById(Long id);

}
