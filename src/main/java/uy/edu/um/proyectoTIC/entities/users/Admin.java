package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class Admin extends Account {

    @Builder
    public Admin(String email, String name, String address, LocalDate birthdate,String password) {
        super(email, name, address, birthdate,password);
    }
}
