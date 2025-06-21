package mx.edu.utez.airbnb_services.controllers.department;

import mx.edu.utez.airbnb_services.controllers.person.PersonDto;
import mx.edu.utez.airbnb_services.models.department.Department;
import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.services.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-airbnb/department/")
@CrossOrigin(origins = {"*"})
public class DepartmentController {
    @Autowired
    private DepartmentService service;
    @GetMapping("/")
    public List<Department> getAll() {
        return this.service.getAll();
    }
    @GetMapping("/{id}")
    public Department getOne(@PathVariable("id") Long id) {
        return this.service.getOne(id);
    }
    @PostMapping("/")
    public Department save(@RequestBody DepartmentDto department) {
        return this.service.insert(department.getDepartment());
    }
    @PutMapping("/")
    public Department update(@RequestBody DepartmentDto department) {
        return this.service.update(department.getDepartment());
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return this.service.deleteOne(id);
    }
}
