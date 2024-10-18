package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.users.Client;

public interface ClientRepository extends JpaRepository<Client,String> {
}
