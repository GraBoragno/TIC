package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Pelicula")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoPelicula;

    private String nombreDirector;

    private Long duracion; //Usar minutos

    private String anio;

    private boolean subtitulada;

    private String nombrePelicula;

    @ElementCollection
    @CollectionTable(name = "generos", joinColumns = @JoinColumn(name = "codigoPelicula"))
    @Column(name = "tipoGenero")
    private List<String> generos = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cine_pelicula", joinColumns = @JoinColumn(name = "codigoPelicula"), inverseJoinColumns = @JoinColumn(name = "IdSucursal"))
    @Builder.Default
    private List<Cine> cineDondeEsta = new LinkedList<>();
}
