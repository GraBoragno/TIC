package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private Integer roomNbr;

    @Transient  //No lo mapea en la base de datos
    @Builder.Default
    private int[][] seats = new int[15][10];
}
