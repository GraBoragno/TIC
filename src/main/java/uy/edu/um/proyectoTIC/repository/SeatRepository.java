package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
