package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;
import uy.edu.um.proyectoTIC.entities.users.Client;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketCode;

    private String cTName;

    private Long tCentralId;

    private Long ticketPrice; //se calcula

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broadcastId")
    private Broadcast ticketBroadcast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rowColumn")
    private Seat assignedSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Client clientTicket;

    @ManyToMany
    @JoinTable(name = "ticket_combo_admit", joinColumns = @JoinColumn(name = "ticketCode"), inverseJoinColumns = @JoinColumn(name = "comboId"))
    private List<Combo> admittedCombos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "snack_contained", joinColumns = @JoinColumn(name = "ticketCode"), inverseJoinColumns = @JoinColumn(name = "snackName"))
    private List<Snack> snacks = new ArrayList<>();



}
