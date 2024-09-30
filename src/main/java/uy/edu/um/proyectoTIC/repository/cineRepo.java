package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Cine;

public interface cineRepo extends JpaRepository<Cine, Long>{
}
