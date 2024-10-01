package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.edu.um.proyectoTIC.entities.users.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
