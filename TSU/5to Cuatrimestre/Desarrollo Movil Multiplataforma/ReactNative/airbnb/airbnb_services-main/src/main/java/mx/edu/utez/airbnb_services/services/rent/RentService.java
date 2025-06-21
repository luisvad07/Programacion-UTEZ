package mx.edu.utez.airbnb_services.services.rent;

import mx.edu.utez.airbnb_services.models.rent.Rent;
import mx.edu.utez.airbnb_services.models.rent.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class RentService {
    @Autowired
    private RentRepository repository;
    @Transactional(readOnly = true)
    public List<Rent> getAll(){
        return this.repository.findAll();
    }
    @Transactional(readOnly = true)
    public Rent getOne(Long id){
        return this.repository.findById(id).get();
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Rent insert (Rent rent) {
        return this.repository.saveAndFlush(rent);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Rent save (Rent rent) {
        return this.repository.saveAndFlush(rent);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Rent update (Rent rent) {
        return this.repository.saveAndFlush(rent);
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
