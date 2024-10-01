package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Account {

    private Long cardNbr;
}
