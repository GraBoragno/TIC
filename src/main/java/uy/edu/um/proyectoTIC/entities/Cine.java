package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Cine")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cine implements Serializable {

    @Id
    @Column(name = "IdSucursal")
    private Long idSuc;

    @Column(name = "NroSalas")
    private Integer nroSalas;

    @Column(name = "Barrio")
    private String barrio;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cine_comida", joinColumns = @JoinColumn(name = "IdSucursal"), inverseJoinColumns = @JoinColumn(name = "nombreComida"))
    @Builder.Default
    private List<Comida> comidas = new LinkedList<>();

    @OneToMany(mappedBy = "cineSala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Sala> salas = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Pelicula> peliculas = new LinkedList<>(); //Cambiar a Hash o algo
}
