package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import uy.edu.um.proyectoTIC.entities.Broadcast;

import java.time.LocalDate;
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

    private String filmName;

    private String directorName;

    private Long duration; //En minutos

    private LocalDate releaseYear;

    @ElementCollection
    @Builder.Default
    private List<String> genres = new ArrayList<>();

    @OneToMany(mappedBy = "broadcastFilm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Broadcast> screening = new ArrayList<>();
}
