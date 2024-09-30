package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Comida")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comida {

    @Id
    @Column(name = "Nombre comida")
    private String nombreComida;

    @Column(name = "Precio")
    private Long precio;

    @ManyToMany(mappedBy = "comidas" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Cine> cines = new LinkedList<Cine>();
}
