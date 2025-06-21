package mx.edu.utez.servicioEscolar.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.cita.Cita;
import mx.edu.utez.servicioEscolar.models.solicitante.Solicitante;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SolicitanteDto {

    private Long id;
    private String matricula;
    private String nombre;
    private String apeMaterno;
    private String apePaterno;
    private String correo;
    private String passwordSoli;
    private int telefono;
    private Boolean status;

    private Admin admin;

    private List<Cita> citas;

    public Solicitante getSolicitante(){
        return new Solicitante(
                getId(),
                getMatricula(),
                getNombre(),
                getApeMaterno(),
                getApePaterno(),
                getCorreo(),
                getPasswordSoli(),
                getTelefono(),
                getStatus(),
                getAdmin(),
                getCitas()
                );
    }


}
