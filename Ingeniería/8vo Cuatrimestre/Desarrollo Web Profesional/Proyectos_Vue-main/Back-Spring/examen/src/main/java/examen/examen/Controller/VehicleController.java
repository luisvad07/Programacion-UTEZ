package examen.examen.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import examen.examen.Model.Vehicle;
import examen.examen.Service.VehicleService;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @PostMapping
    public Vehicle saveVehicle(@RequestBody(required = true) Vehicle vehicle) {
        return service.saveVehicle(vehicle);
    }

    @PostMapping("/page")
    Page<Vehicle> getPage(Pageable pageable) {
        return service.PageOfVehicle(pageable);
    }
}
