package mx.edu.utez.airbnb_services.services.department;

import mx.edu.utez.airbnb_services.models.department.Department;
import mx.edu.utez.airbnb_services.models.department.DepartmentRepository;
import mx.edu.utez.airbnb_services.models.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    private DepartmentRepository repository;

    @Transactional(readOnly = true)
    public List<Department> getAll(){
        return this.repository.findAll();
    }
    @Transactional(readOnly = true)
    public Department getOne(Long id){
        return this.repository.findById(id).get();
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Department insert (Department department) {
        return this.repository.saveAndFlush(department);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Department update (Department department) {
        return this.repository.saveAndFlush(department);
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
