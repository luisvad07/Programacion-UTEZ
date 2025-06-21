package mx.edu.utez.airbnb_services.services.person;

import mx.edu.utez.airbnb_services.models.person.Person;
import mx.edu.utez.airbnb_services.models.person.PersonRepository;
import mx.edu.utez.airbnb_services.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository repository;

    @Transactional(readOnly = true)
    public List<Person> getAll(){
        return this.repository.findAll();
    }
    @Transactional(readOnly = true)
    public Person getOne(Long id){
        return this.repository.findById(id).get();
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Person insert (Person person) {
        return this.repository.saveAndFlush(person);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Boolean update (Person person) {
        return this.repository.updatePersonById(person.getFullname(),person.getBirthday(),person.getId())>0;
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
