package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmCode;

    private String directorName;

    private Long duration; //Usar minutos

    private String year;

    private boolean subtitled;

    private String filmName;

    @ElementCollection
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "filmCode"))
    @Column(name = "genre")
    private List<String> genres = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cinemaFilm", joinColumns = @JoinColumn(name = "filmCode"), inverseJoinColumns = @JoinColumn(name = "IdSucursal"))
    @Builder.Default
    private List<Cinema> cinemaAppearance = new LinkedList<>();
}
