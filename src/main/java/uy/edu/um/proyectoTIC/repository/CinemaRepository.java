package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Cinema;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findByCentralIdIn(List<Long> centralIds);
}
