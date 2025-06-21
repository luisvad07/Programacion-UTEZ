package examen.examen.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import examen.examen.Model.Vehicle;
import examen.examen.Repository.VehicleRepository;

@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository repo;

    public Vehicle saveVehicle(Vehicle bean){
        return repo.save(bean);
    }

    public Page<Vehicle> PageOfVehicle(Pageable page){
        return repo.findAll(page);
    }
}
