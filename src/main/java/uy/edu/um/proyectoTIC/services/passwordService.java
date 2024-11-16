package uy.edu.um.proyectoTIC.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class passwordService {

    public String hashPassword(String password) {
        // Genera un hash para la contraseña
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public boolean checkPassword(String password, String hashedPassword) {
        // Compara la contraseña proporcionada con el hash almacenado
        return BCrypt.checkpw(password, hashedPassword);
    }
}
