package mx.edu.utez.servicioEscolar.services;


import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.admin.AdminRepository;
import mx.edu.utez.servicioEscolar.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Admin>> getAll(){
        return new CustomResponse<>(
                this.adminRepository.findAll(),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Admin>> getAllActive(){
        return new CustomResponse<>(
                this.adminRepository.findAllByStatus(true),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Admin>> getAllInactive(){
        return new CustomResponse<>(
                this.adminRepository.findAllByStatus(false),
                false,
                200,
                "ok"
        );
    }

    ///Servicio para buscar uno
    @Transactional(readOnly = true)
    public CustomResponse<Admin> getOne(Long id) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            return new CustomResponse<>(
                    adminOptional.get(),
                    false,
                    200,
                    "ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Administrador no encontrada"
            );
        }
    }

    //Servicio para insertar
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Admin> insert(Admin admin){
        if (this.adminRepository.existsByCorreoAdmin(admin.getCorreoAdmin())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El correo ya existe"
            );
        }
        return new CustomResponse<>(
                this.adminRepository.saveAndFlush(admin),
                false,
                200,
                "Administrador registrado correctamente"
        );
    }

    //Servicio para actualizar
    @Transactional (rollbackFor = {SQLException.class})
    public  CustomResponse<Admin> update(Admin admin){
        if (!this.adminRepository.existsById(admin.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El administrador no existe"
            );
        }
        return new CustomResponse<>(
                this.adminRepository.saveAndFlush(admin),
                false,
                200,
                "Administrador actualizado correctamente"
        );
    }

    //Servicio para cambiar el status
    @Transactional (rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Admin admin){
        if (!this.adminRepository.existsById(admin.getId())) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El administrador no existe"
            );
        }
        return new CustomResponse<>(
                this.adminRepository.updateStatusById(admin.getStatus(), admin.getId()
                ) == 1,
                false,
                200,
                "Se ha cambiado el status del usuario Departamento"
        );
    }

    public Admin findByCorreoAdmin(String correoAdmin){
        return this.adminRepository.findByCorreoAdmin(correoAdmin);
    }

}
