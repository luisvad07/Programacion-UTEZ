package mx.edu.utez.redre.services;

import mx.edu.utez.redre.models.asesor.Asesor;
import mx.edu.utez.redre.models.asesor.AsesorRepository;
import mx.edu.utez.redre.models.departamento.Departamento;
import mx.edu.utez.redre.models.departamento.DepartamentoRepository;
import mx.edu.utez.redre.models.responsables.Responsables;
import mx.edu.utez.redre.models.responsables.ResponsablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.redre.models.estudiantes.Estudiantes;
import mx.edu.utez.redre.models.estudiantes.EstudiantesRespository;
import mx.edu.utez.redre.utils.CustomResponse;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class EstudiantesServices {
    @Autowired
    EstudiantesRespository repository;

    @Autowired
    AsesorRepository asesorRepository;
    @Autowired
    ResponsablesRepository responsablesRepository;
    @Autowired
    DepartamentoRepository departamentoRepository;

    @Transactional
    public CustomResponse<List<Estudiantes>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Estudiantes>> getAllActive(){
        return new CustomResponse<>(
                this.repository.findAllByStatus(true),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Estudiantes>> getAllByAsesor(Long asesorId){
        Asesor asesor = asesorRepository.getById(asesorId);
        return new CustomResponse<>(
                this.repository.findAllByAsesor(asesor),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Estudiantes>> findAllByAsesorAndUrlReporteStatus(Long asesorId){
        Asesor asesor = asesorRepository.getById(asesorId);
        return new CustomResponse<>(
                this.repository.findAllByAsesorAndReportStatus(asesor, "esperaRespuestaDeAsesor"),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Estudiantes>> findAllByAsesor_ResponsableAndReportStatus(Long asesorId){
        Responsables responsables = responsablesRepository.getById(asesorId);
        return new CustomResponse<>(
                this.repository.findAllByAsesor_ResponsableAndReportStatus(responsables, "aprobadoPorAsesor"),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Estudiantes>> findAllByAsesor_Responsable_DepartamentoAndReportStatus(Long asesorId){
        Departamento departamento = departamentoRepository.getById(asesorId);
        return new CustomResponse<>(
                this.repository.findAllByAsesor_Responsable_DepartamentoAndReportStatus(departamento, "aprobadoPorResponsable"),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Estudiantes> getOne(Long id){
        return new CustomResponse<>(
                this.repository.findById(id).get(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Estudiantes> getByMatricula(String matricula){
        return new CustomResponse<>(
                this.repository.getEstudiantesByMatricula(matricula),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Estudiantes> insert(Estudiantes estudiante){
        if(this.repository.existsByCorreo(estudiante.getCorreo()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El Correo asignado a este estudiante ya se encuentra registrado"
            );
        if(this.repository.existsByMatricula(estudiante.getMatricula()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "La Matricula asignada a este estudiante ya se encuentra registrada"
            );
        try {
            repository.save(estudiante);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CustomResponse<>(
                this.repository.saveAndFlush(estudiante),
                false,
                200,
                "Estudiante registrado correctamente"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Estudiantes> update(Estudiantes estudiante){
        if(!this.repository.existsById(estudiante.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El usuario Estudiante no existe"
            );

        return new CustomResponse<>(
                this.repository.saveAndFlush(estudiante),
                false,
                200,
                "Estudiante actualizado correctamente"
        );
    }

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Estudiantes estudiante){
        if(!this.repository.existsById(estudiante.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "El usuario Estudiante no existe"
            );
        }
        return new CustomResponse<>(
                this.repository.updateStatusById(
                        estudiante.getStatus(), estudiante.getId()
                ) == 1,
                false,
                200,
                "Estudiante actualizado correctamente"
        );
    }

    public Estudiantes findByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }
}
