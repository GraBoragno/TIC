package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.Food;

public interface FoodRepo extends JpaRepository<Food, String> {
}
