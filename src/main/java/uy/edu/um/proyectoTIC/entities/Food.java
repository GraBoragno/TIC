package uy.edu.um.proyectoTIC.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    private String foodName;

    private Long price;

    @ManyToMany(mappedBy = "foods" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Cinema> cinemas = new LinkedList<Cinema>();
}
