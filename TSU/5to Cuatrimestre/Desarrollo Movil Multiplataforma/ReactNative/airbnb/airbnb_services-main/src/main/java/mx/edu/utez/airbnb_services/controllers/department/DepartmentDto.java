package mx.edu.utez.airbnb_services.controllers.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.airbnb_services.models.department.Department;
import mx.edu.utez.airbnb_services.models.rent.Rent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentDto {
    private Long id;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 80, message = "Máximo 8 carácteres.")
    private String name;
    @NotEmpty(message = "Campo obligatorio")
    private String location;
    @NotEmpty(message = "Campo obligatorio")
    private String images;
    @NotEmpty(message = "Campo obligatorio")
    @Size(min = 1, max = 255, message = "Máximo 255 carácteres.")
    private String description;
    private Double rating;
    private Double quantity_rating;
    private Double price;
    private List<Rent> rent;

    public Department getDepartment(){
        return new Department(
                getId(),
                getName(),
                getLocation(),
                getImages(),
                getDescription(),
                getRating(),
                getQuantity_rating(),
                getPrice(),
                getRent()
        );
    }
}
