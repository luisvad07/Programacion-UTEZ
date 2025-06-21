package mx.edu.utez.airbnb_services.controllers.user;

import mx.edu.utez.airbnb_services.models.user.User;
import mx.edu.utez.airbnb_services.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-airbnb/user/")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/")
    public List<User> getAll() {
        return this.service.getAll();
    }
    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        return this.service.getOne(id);
    }
    @PostMapping("/")
    public User save(@RequestBody UserDto user) {
        return this.service.insert(user.getUser());
    }
    @PutMapping("/")
    public User update(@RequestBody UserDto user) {
        return this.service.update(user.getUser());
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return this.service.deleteOne(id);
    }
}
