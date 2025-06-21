package mx.edu.utez.airbnb_services.models.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.airbnb_services.models.rent.Rent;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "JSON")
    private String location;

    @Column(nullable = false, columnDefinition = "JSON")
    private String images;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column()
    private Double rating;

    @Column()
    private Double quantity_rating;

    @Column()
    private Double price;

    //Relación con rents
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("department")
    private List<Rent> rent;
}
