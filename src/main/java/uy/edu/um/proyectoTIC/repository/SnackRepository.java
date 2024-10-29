package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.edu.um.proyectoTIC.entities.Snack;

import java.util.List;

public interface SnackRepository extends JpaRepository<Snack, String> {
}
