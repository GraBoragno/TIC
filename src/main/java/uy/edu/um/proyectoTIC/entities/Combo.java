package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comboId;

    private String comboName;

    private Long comboPrice;

    @ManyToMany(mappedBy = "admittedCombos")
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "comboContent", joinColumns = @JoinColumn(name = "comboId"), inverseJoinColumns = @JoinColumn(name = "snackName"))
    private List<Snack> snacksIncluded = new ArrayList<>();
}
