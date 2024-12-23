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
public class Room {

    //Se cambió
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roomNbr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centralId") //No va el Buider.Default porque se especifica el cine al crearla
    private Cinema cinemaRoom;


    @OneToMany(mappedBy = "hasRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Broadcast> has = new ArrayList<>();
}
