package examen.examen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import examen.examen.Model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
}
