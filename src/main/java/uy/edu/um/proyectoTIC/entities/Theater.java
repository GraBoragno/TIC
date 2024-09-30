package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theater {

    @Id
    private Integer theaterNbr;

    @ElementCollection
    @CollectionTable(name = "seats", joinColumns = @JoinColumn(name = "theaterNbr"))
    private List<Integer> seats = new ArrayList<>(); //Cambiar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centralId") //No va el Buider.Default porque se especifica el cine al crearla
    private Cinema cinemaTheater;
}
