package utez.edu.mx.foodster.dtos.direccionesusuarios;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.foodster.entities.direcciones.Direcciones;
import utez.edu.mx.foodster.entities.direccionesusuario.DireccionesUsuario;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DireccionesUsuariosDto {

    private String idDireccionUsuario;

    private Direcciones direcciones;

    private Usuarios usuarios;

    private Timestamp ultimaModificacion;

    private Boolean active;

    public DireccionesUsuario toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new DireccionesUsuario(idDireccionUsuario, direcciones, usuarios, ultimaModificacion, active);
    }
}
