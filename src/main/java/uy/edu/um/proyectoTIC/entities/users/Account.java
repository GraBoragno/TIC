package uy.edu.um.proyectoTIC.entities.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Account {

    @Email
    @Id
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    private String password;

}
