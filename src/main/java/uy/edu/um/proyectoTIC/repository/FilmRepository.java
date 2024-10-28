package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.edu.um.proyectoTIC.entities.Film;

import java.util.List;
import java.time.LocalDateTime;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("""
        SELECT f 
        FROM Film f 
        JOIN f.screening b 
        WHERE b.dateTime > :currentDate
        """)
    List<Film> findAvailableFilms(LocalDateTime currentDate);

    @Query("""
        SELECT f 
        FROM Film f  
        WHERE f.filmName > :filmName
        """)
    Film findByFilmName(String filmName);
}
