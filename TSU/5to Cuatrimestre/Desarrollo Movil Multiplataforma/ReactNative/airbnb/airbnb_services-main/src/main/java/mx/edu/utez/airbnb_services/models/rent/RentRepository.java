package mx.edu.utez.airbnb_services.models.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Modifying
    @Query(value = "INSERT INTO RENTS(description,initial_date,final_date,department_id,user_id) " +
            "VALUES (:description,:initial_date,:final_date,:department_id,:user_id)", nativeQuery = true)
    int insert(
            @Param("description") String description,
            @Param("initial_date") String initial_date,
            @Param("final_date") String final_date,
            @Param("department_id") Long department_id,
            @Param("user_id") Long user_id);
}
