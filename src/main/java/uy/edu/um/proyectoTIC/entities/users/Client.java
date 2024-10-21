package uy.edu.um.proyectoTIC.entities.users;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import uy.edu.um.proyectoTIC.entities.Ticket;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Account {

    @Nullable      //Hace que se establezca como nulo por defecto si no se lo pasa como parametro
    private Long cardNbr;

    @Builder
    public Client(String email, String name, String address, LocalDate birthdate, Long cardNbr) {
        super(email, name, address, birthdate);
        this.cardNbr = cardNbr;
    }

    @OneToMany(mappedBy = "clientTicket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsBought;
}
