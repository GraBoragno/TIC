package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import uy.edu.um.proyectoTIC.entities.Ticket;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Account {

    private Long cardNbr;

    @OneToMany(mappedBy = "clientTicket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsBought;
}
