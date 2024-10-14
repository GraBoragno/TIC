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
public class Cinema {

    @Id
    private Long centralId;

    private Integer roomQty;

    private String neighborhood;

    @Transient
    @Builder.Default//Capaz esto no va, ya veremos
    private List<Snack> cinemaSnacks = new LinkedList<>(); //Usar un findAll() de Snack al crear la lista

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> roomsCollection = new ArrayList<>();
}
