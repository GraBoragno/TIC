package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "snacks")
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany(mappedBy = "snacksIncluded")
    private List<Combo> combos = new ArrayList<>();
}
