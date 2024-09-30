package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sala")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sala {

    @Id
    private Integer nroSala;

    @ElementCollection
    @CollectionTable(name = "asientos", joinColumns = @JoinColumn(name = "nroSala"))
    @Column(name = "numero")
    private List<Integer> asientos = new ArrayList<>(); //Cambiar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSucursal") //No va el Buider.Default porque se especifica el cine al crearla
    private Cine cineSala;
}
