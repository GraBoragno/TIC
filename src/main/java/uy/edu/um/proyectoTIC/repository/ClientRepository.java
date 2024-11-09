package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uy.edu.um.proyectoTIC.entities.Ticket;
import uy.edu.um.proyectoTIC.entities.users.Client;

import java.util.List;

@Repository
public interface
ClientRepository extends JpaRepository<Client,String> {

    @Query("""
    SELECT t 
    FROM Ticket t 
    WHERE t.clientTicket.email = :email
    """)
    List<Ticket> findTicketsByEmail(@Param("email") String email);

}
