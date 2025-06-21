package utez.edu.mx.foodster.dtos.tarjetas;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import utez.edu.mx.foodster.entities.tarjetas.Tarjetas;
import utez.edu.mx.foodster.entities.usuarios.Usuarios;
import utez.edu.mx.foodster.validation.BeforeCurrentMonthYear;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarjetasDto {
    private String idTarjeta;
    @NotNull(message = "El usuario es obligatorio")
    private Usuarios usuario;
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Max(value = 16, message = "El número de tarjeta debe tener 16 dígitos")
    @Min(value = 16, message = "El número de tarjeta debe tener 16 dígitos")
    private String numeroTarjeta;
    @NotBlank(message = "El nombre de la tarjeta es obligatorio")
    private String nombreTarjeta;
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @BeforeCurrentMonthYear(message = "La fecha de vencimiento debe ser antes del mes y año actual")
    private Date fechaVencimiento;
    @NotBlank(message = "El CVV es obligatorio")
    @Max(value = 3, message = "El CVV debe tener 3 dígitos")
    @Min(value = 3, message = "El CVV debe tener 3 dígitos")
    private String cvv;
    private Timestamp ultimaModificacion;
    private Boolean active;

    public Tarjetas toEntity() {
        this.ultimaModificacion = new Timestamp(System.currentTimeMillis());
        return new Tarjetas(idTarjeta, usuario, numeroTarjeta, nombreTarjeta, fechaVencimiento, cvv, ultimaModificacion, active);
    }
}
