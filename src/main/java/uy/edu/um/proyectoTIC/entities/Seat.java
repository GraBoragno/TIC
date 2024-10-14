package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.Id;
import jakarta.persistence.IdClass;


@IdClass(Seat.class)
public class Seat {
    @Id
    private String rowColumn;

}
