package utez.edu.mx.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "generos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Generos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gen;

    @Column(nullable = false, length = 100)
    private String name;
}
