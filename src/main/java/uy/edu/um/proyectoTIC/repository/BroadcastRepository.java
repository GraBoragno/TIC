package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast,Long> {
}