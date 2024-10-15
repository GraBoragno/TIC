package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Broadcast {

    @Id
    private Long broadcastId;
    private LocalDate dateTime;
    private Long broadcastPrice;

    @Transient  //No lo mapea en la base de datos
    @Builder.Default
    private int[][] seats = new int[15][10];

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomNbr") //No va el Buider.Default porque se especifica el cine al crearla
    private Room hasRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmCode")
    private Film broadcastFilm;

    @OneToMany(mappedBy = "ticketBroadcast", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsIncluded = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "broadcastSeat", joinColumns = @JoinColumn(name = "broadcastId"), inverseJoinColumns = @JoinColumn(name = "rowColumn"))
    private List<Seat> availableSeats = new ArrayList<>();




}
