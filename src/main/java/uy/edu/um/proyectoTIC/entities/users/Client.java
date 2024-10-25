package uy.edu.um.proyectoTIC.entities.users;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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
    public Client(String email, String name, String address, LocalDate birthdate,String password, Long cardNbr) {
        super(email, name, address, birthdate, password);
        this.cardNbr = cardNbr;
    }

    @Transient ////Ver si funciona
    @OneToMany(mappedBy = "clientTicket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> ticketsBought;
     //Hash
}
