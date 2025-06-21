package mx.edu.utez.airbnb_services.services.user;

import mx.edu.utez.airbnb_services.models.user.User;
import mx.edu.utez.airbnb_services.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return this.repository.findAll();
    }
    @Transactional(readOnly = true)
    public User getOne(Long id){
        return this.repository.findById(id).get();
    }
    @Transactional(rollbackFor = {SQLException.class})
    public User insert (User user) {
        return this.repository.saveAndFlush(user);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public User update (User user) {
        return this.repository.saveAndFlush(user);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public boolean deleteOne(Long id){
        if (!(this.repository.existsById(id))){
            return false;
        }
        this.repository.deleteById((id));
        return true;
    }
}
