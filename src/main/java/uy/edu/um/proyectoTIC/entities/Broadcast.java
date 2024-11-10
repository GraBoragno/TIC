package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long broadcastId;
    private LocalDateTime dateTime;
    private Long broadcastPrice;
    private Long centralId;

    @ElementCollection
    @Fetch(FetchMode.JOIN)  // Indica que se debe realizar un fetch eager con un join
    private List<Long> assignedSeatsId = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomNbr") //No va el Buider.Default porque se especifica el cine al crearla
    private Room hasRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmCode")
    private Film broadcastFilm;

    @OneToMany(mappedBy = "ticketBroadcast", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsIncluded = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "broadcastSeat", joinColumns = @JoinColumn(name = "broadcastId"), inverseJoinColumns = @JoinColumn(name = "seatId"))
    private List<Seat> availableSeats = new ArrayList<>();

}
