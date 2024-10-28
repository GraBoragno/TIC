package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.services.ClientService;

@Controller
public class LoginController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/log-in")
    public String showLoginForm()
    {
        return "login"; // Muestra el formulario de login
    }



    @PostMapping("/log-in")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) throws EntityNotFoundException
    {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            session.setAttribute("user", client); // Guarda el usuario en la sesión
            return "redirect:/home";
        } else {
            return "redirect:/log-in?error=true"; // Redirige a login con mensaje de error
        }
    }

}

