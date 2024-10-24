package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.edu.um.proyectoTIC.entities.Film;

import uy.edu.um.proyectoTIC.entities.Broadcast;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("""
        SELECT f 
        FROM Film f 
        JOIN f.screening b 
        WHERE b.dateTime > :currentDate
        """)
    List<Film> findAvailableFilms(LocalDateTime currentDate);
}
