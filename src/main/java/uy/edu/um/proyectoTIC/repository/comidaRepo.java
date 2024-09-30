package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Comida;

public interface comidaRepo extends JpaRepository<Comida, String> {
}
