package mx.edu.utez.redre.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.models.departamento.DepartamentoRepository;
import mx.edu.utez.redre.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class DepartamentoServices {
    @Autowired
    private DepartamentoRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Departamento>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Departamento>> getAllActive(){
        return new CustomResponse<>(
                this.repository.findAllByStatus(true),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Departamento>> getByDivision(String division){
        return new CustomResponse<>(
                this.repository.findAllByDivisionAcademica(division),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Departamento> getOne(Long id){
        return new CustomResponse<>(
                this.repository.findById(id).get(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Departamento> insert(Departamento departamento){
        if (this.repository.existsByCorreo(departamento.getCorreo())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Este usuario departamento ya se encuentra regitrado"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(departamento),
                false,
                200,
                "Usuario departamento registrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Departamento> update(Departamento departamento){
        if (!this.repository.existsById(departamento.getId())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Este usuario Departamento no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(departamento),
                false,
                200,
                "Usuario Departamento Actualizado correctamente"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Departamento departamento){
        if(!this.repository.existsById(departamento.getId())){
            return new CustomResponse<>(
                    null, true, 400, "El usuario Departamento no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.updateStatusById(departamento.getStatus(), departamento.getId()) == 1,
                false,
                200,
                "Se ha cambiado el status del usuario Departamento"
        );
    }

    public Departamento findByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }
}
