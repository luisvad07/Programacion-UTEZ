package mx.edu.utez.redre.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.redre.models.responsables.ResponsablesRepository;
import mx.edu.utez.redre.models.responsables.Responsables;
import mx.edu.utez.redre.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class ResponsablesServices {
    @Autowired
    private ResponsablesRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Responsables>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Responsables>> getAllActive(){
        return new CustomResponse<>(
                this.repository.findAllByStatus(true),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Responsables> getOne(Long id){
        return new CustomResponse<>(
                this.repository.findById(id).get(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Responsables> insert(Responsables responsable){
        if(this.repository.existsByCorreo(responsable.getCorreo()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El Correo asignado a este responsable ya se encuentra registrado"
            );
        return new CustomResponse<>(
                this.repository.saveAndFlush(responsable),
                false,
                200,
                "Responsable registrado correctamente"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Responsables> update(Responsables responsable){
        if(!this.repository.existsById(responsable.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario responsable no existe"
            );
        return new CustomResponse<>(
                this.repository.saveAndFlush(responsable),
                false,
                200,
                "Responsable actualizado correctamente"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Responsables responsable){
        if(!this.repository.existsById(responsable.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "El usuario responsable no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.updateStatusById(
                        responsable.getStatus(), responsable.getId()
                ) == 1,
                false,
                200,
                "Responsable actualizado correctamente"
        );
    }

    public Responsables findByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }
}
