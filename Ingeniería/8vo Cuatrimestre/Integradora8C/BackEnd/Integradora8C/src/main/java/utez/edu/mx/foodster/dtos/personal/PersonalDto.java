package utez.edu.mx.foodster.dtos.personal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.categoriaspersonal.CategoriasPersonal;
import utez.edu.mx.foodster.entities.personal.Personal;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDto {

    private String idPersonal;

    private Usuarios usuarios;

    private CategoriasPersonal categoria;

    private Timestamp ultimaModificacion;

    private Boolean active;

    public Personal toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Personal(idPersonal, usuarios, categoria, ultimaModificacion, active);
    }
}