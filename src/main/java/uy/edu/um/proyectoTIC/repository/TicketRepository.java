package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
