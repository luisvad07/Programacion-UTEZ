package utez.edu.mx.movies.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "peliculas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Peliculas{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_movie;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String directorMovie;

    @Column(nullable = false)
    private long duration;

    @ManyToOne
    @JoinColumn(name = "fk_genero")
    private Generos genero;

    @Column(nullable = false)
    private Date fechaPublicacion; // Nuevo campo para la fecha de publicación
}
