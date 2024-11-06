package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.edu.um.proyectoTIC.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("""
        SELECT r.id FROM Room r
        JOIN r.cinemaRoom c
        WHERE c.centralId = :centralId
        AND r.roomNbr = :roomNbr
    """)
    Long findRoomIdByCentralIdAndRoomNbr(
            @Param("centralId") Long centralId,
            @Param("roomNbr") Integer roomNbr
    );

    @Query("""
        SELECT r FROM Room r
        JOIN r.cinemaRoom c
        WHERE c.centralId = :centralId
        AND r.id = :id
    """)
    Room findRoomByCentralIdAndId(
            @Param("centralId") Long centralId,
            @Param("id") Long id
    );

}
