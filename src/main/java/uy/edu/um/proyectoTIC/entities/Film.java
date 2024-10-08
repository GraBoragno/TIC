package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Transient
    @Builder.Default
    private List<String> genres = new ArrayList<>();
}
