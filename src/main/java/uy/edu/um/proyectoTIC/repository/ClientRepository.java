package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.edu.um.proyectoTIC.entities.users.Client;
@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
}
