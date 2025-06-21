package mx.edu.utez.airbnb_services.controllers.rent;

import mx.edu.utez.airbnb_services.controllers.person.PersonDto;
import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.models.rent.Rent;
import mx.edu.utez.airbnb_services.services.rent.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-airbnb/rent/")
@CrossOrigin(origins = {"*"})
public class RentController {
    @Autowired
    private RentService service;
    @GetMapping("/")
    public List<Rent> getAll() {
        return this.service.getAll();
    }
    @GetMapping("/{id}")
    public Rent getOne(@PathVariable("id") Long id) {
        return this.service.getOne(id);
    }
    @PostMapping("/")
    public Rent save(@RequestBody RentDto rent) {
        return this.service.save(rent.getRent());
    }
    @PutMapping("/")
    public Rent update(@RequestBody RentDto rent) {
        return this.service.update(rent.getRent());
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return this.service.deleteOne(id);
    }
}
