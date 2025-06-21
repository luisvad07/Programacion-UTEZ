package com.school.citas.dtos;

import com.school.citas.models.Administrador.Administrador;
import com.school.citas.models.Cita.Cita;
import com.school.citas.models.Servicio.Servicio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double costo;
    private Boolean status;

    private Administrador admin;
    private List<Cita> citas;
    public Servicio getServicio(){
        return new Servicio(
                getId(),
                getNomserv(),
                getDescripcion(),
                getDocumentos(),
                getCosto(),
                getStatus(),
                getAdmin(),
                getCitas()

        );
    }
}
