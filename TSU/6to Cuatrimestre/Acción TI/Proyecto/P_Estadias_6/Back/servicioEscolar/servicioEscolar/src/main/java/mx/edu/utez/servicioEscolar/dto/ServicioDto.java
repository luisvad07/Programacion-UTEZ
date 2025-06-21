package mx.edu.utez.servicioEscolar.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.servicioEscolar.models.admin.Admin;
import mx.edu.utez.servicioEscolar.models.cita.Cita;
import mx.edu.utez.servicioEscolar.models.servicio.Servicio;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicioDto {

    private Long id;
    private String nomserv;
    private String descripcion;
    private String documentos;
    private int costo;
    private Boolean status;
    private List<Cita> citas;
    private Admin admin;

    public Servicio getServicio(){
        return new Servicio(
                getId(),
                getNomserv(),
                getDescripcion(),
                getDocumentos(),
                getCosto(),
                getStatus(),
                getCitas(),
                getAdmin()
        );
    }
}
