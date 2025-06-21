package mx.edu.utez.airbnb_services.models.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying
    @Query(value = "UPDATE people SET fullname = :fullname, birthday = :birthday WHERE id = :id", nativeQuery = true)
    int updatePersonById(
            @Param("fullname") String status,
            @Param("birthday") String birthday,
            @Param("id")Long id);
}
