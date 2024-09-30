package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Theater;

public interface TheaterRepo extends JpaRepository<Theater, Integer> {
}
