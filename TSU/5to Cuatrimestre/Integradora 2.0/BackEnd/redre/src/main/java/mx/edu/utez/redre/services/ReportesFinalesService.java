package mx.edu.utez.redre.services;

import mx.edu.utez.redre.models.reportesFinales.ReportesFinales;
import mx.edu.utez.redre.models.reportesFinales.ReportesFinalesRepository;
import mx.edu.utez.redre.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReportesFinalesService {
    @Autowired
    ReportesFinalesRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<ReportesFinales> getReporteByMatricula(String division){
        return new CustomResponse<>(
                this.repository.findByMatricula(division),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<ReportesFinales>> getAllByDivisionAcademica(String division){
        return new CustomResponse<>(
                this.repository.getAllByDivisionAcademica(division),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<ReportesFinales>> getAllByDivisionAndReporteING(String division){
        return new CustomResponse<>(
                this.repository.getAllByDivisionAcademicaAndUrlReporteINGNotNull(division),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<ReportesFinales> insert(ReportesFinales reportesFinales){
        if (this.repository.existsByMatricula(reportesFinales.getMatricula())){
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "Ya existe el reporte"
            );
        }
        try{
            repository.save(reportesFinales);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(reportesFinales),
                false,
                200,
                "Se subio el reporte final"
        );
    }
}
