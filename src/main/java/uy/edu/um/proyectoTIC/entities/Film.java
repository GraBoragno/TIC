package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
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

    @Column(unique = true)
    private String filmName;

    private String directorName;

    private Long duration; //En minutos

    private Year releaseYear;

    @ElementCollection
    @Builder.Default
    private List<String> genres = new ArrayList<>();

    @OneToMany(mappedBy = "broadcastFilm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Broadcast> screening = new ArrayList<>();
}
