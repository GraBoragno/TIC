package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)  //Crea tablas separadas para cliente y admin
public abstract class Account {

    @Id
    private String email;

    private String name;

    private String address;

    private LocalDate birthdate;

}
