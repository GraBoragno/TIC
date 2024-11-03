package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface BroadcastRepository extends JpaRepository<Broadcast,Long> {

    //query que filtre por pelicula
    @Query("""
        SELECT b FROM Broadcast b
        WHERE b.broadcastFilm.filmName = :filmName
        AND b.dateTime >= :currentDate
    """)
    List<Broadcast> findByFilmNameBroadcast(@Param("filmName") String filmName, @Param("currentDate") LocalDateTime currentDate);

    //query que filtre por peli + cine
    @Query("""
        SELECT b FROM Broadcast b
        WHERE b.broadcastFilm.filmName = :filmName
        AND b.centralId = :centralId
        AND b.dateTime >= :currentDate
    """)
    List<Broadcast> findByFilmCodeAndCentralId(
            @Param("filmName") String filmName,
            @Param("centralId") Long centralId,
            @Param("currentDate") LocalDateTime currentDate
    );

    //query que filtre por lo anterior + hora
    @Query("""
        SELECT b FROM Broadcast b
        WHERE b.broadcastFilm.filmCode = :filmCode AND b.centralId = :centralId AND b.dateTime > :dateTime
    """)
    List<Broadcast> findByFilmCodeAndCentralIdAndDateTime(
            @Param("filmCode") Long filmCode,
            @Param("centralId") Long centralId,
            @Param("dateTime") LocalDateTime dateTime
    );
}
