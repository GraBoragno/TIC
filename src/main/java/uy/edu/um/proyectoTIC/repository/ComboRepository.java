package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Film;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo, Long> {

    @Query("""
        SELECT c
        FROM Combo c 
        """)
    List<Combo> findAvailableCombos();
}
