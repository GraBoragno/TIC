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

public class Seat {
    @Id
    private String rowColumn;

    @ManyToMany(mappedBy = "availableSeats")
    private List<Broadcast> broadcasts = new ArrayList<>();

    @OneToMany(mappedBy = "assignedSeat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();
}
