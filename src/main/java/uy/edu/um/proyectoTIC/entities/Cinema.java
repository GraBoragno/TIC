package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cinema implements Serializable {

    @Id
    private Long centralId;

    private Integer theaterNbr;

    private String neighborhood;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cinemaFood", joinColumns = @JoinColumn(name = "centralId"), inverseJoinColumns = @JoinColumn(name = "foodName"))
    @Builder.Default
    private List<Food> foods = new LinkedList<>();

    @OneToMany(mappedBy = "cinemaTheater", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Theater> theaters = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Film> films = new LinkedList<>(); //Cambiar a Hash o algo
}
