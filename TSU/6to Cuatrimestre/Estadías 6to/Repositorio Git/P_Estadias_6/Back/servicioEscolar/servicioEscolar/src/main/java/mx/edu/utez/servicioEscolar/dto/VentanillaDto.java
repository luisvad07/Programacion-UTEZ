package mx.edu.utez.servicioEscolar.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.cita.Cita;
import mx.edu.utez.servicioEscolar.models.ventanilla.Ventanilla;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentanillaDto {

    private Long id;
    private String nombre;
    private String apeMaterno;
    private String apePaterno;
    private String correo;
    private String password;
    private Boolean status;

    private List<Cita> citas;
    private Admin admin;

    public Ventanilla getVentanilla(){
        return new Ventanilla(
                getId(),
                getNombre(),
                getApeMaterno(),
                getApePaterno(),
                getCorreo(),
                getPassword(),
                getStatus(),
                getCitas(),
                getAdmin()
        );
    }
}
