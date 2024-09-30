package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Sala;

public interface salaRepo extends JpaRepository<Sala, Integer> {
}
