package uy.edu.um.proyectoTIC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uy.edu.um.proyectoTIC.entities.users.Admin;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,String> {

    //devuelve todos los admins
    @Query("""
        SELECT a 
        FROM Admin a
    """)
    List<Admin> findAllAdmins();
}
