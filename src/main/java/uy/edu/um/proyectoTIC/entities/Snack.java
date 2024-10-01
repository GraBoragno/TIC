package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Snack {

    @Id
    private String snackName;

    private String snackPrice;
}
