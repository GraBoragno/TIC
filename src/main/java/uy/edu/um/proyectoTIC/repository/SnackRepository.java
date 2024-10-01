package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Snack;

public interface SnackRepository extends JpaRepository<Snack, String> {
}
