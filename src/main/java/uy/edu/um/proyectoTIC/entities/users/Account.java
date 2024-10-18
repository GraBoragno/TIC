package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Account {

    @Id
    private String email;

    private String name;

    private String address;

    private LocalDate birthdate;

}
