package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Pelicula;

public interface peliculasRepo extends JpaRepository<Pelicula, Long> {
}
