package mx.edu.utez.airbnb_services.controllers.person;

import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-airbnb/person/")
@CrossOrigin(origins = {"*"})
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping("/")
    public List<Person> getAll() {
        return this.service.getAll();
    }
    @GetMapping("/{id}")
    public Person getOne(@PathVariable("id") Long id) {
        return this.service.getOne(id);
    }
    @PostMapping("/")
    public Person save(@RequestBody PersonDto person) {
        return this.service.insert(person.getPerson());
    }
    @PutMapping("/")
    public Boolean update(@RequestBody PersonDto person) {
        return this.service.update(person.getPerson());
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return this.service.deleteOne(id);
    }
}
