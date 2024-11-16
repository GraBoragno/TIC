package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.AdminRepository;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.services.AdminService;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.passwordService;

@Controller
public class LoginController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private passwordService passwordService;

    @GetMapping("/log-in")
    public String showLoginForm() {
        return "login"; // Muestra el formulario de login
    }

    @PostMapping("/log-in")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) throws EntityNotFoundException {


        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("errorEmail", "El correo electrónico no puede estar vacío.");
            return "login"; // Return to login page with error message
        }

        if (!email.contains("@")) {
            model.addAttribute("errorEmail", "El correo electrónico debe ser válido");
            return "login"; // Return to login page with error message
        }

        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("errorPassword", "La contraseña no puede estar vacía.");
            return "login"; // Return to login page with error message
        }

        if (password.length() <= 8 ) {
            model.addAttribute("errorPassword", "La contraseña debe ser 8 o mas caracteres");
            return "login"; // Return to login page with error message
        }



        if (!email.endsWith("@wtf.com")) {
            Client client = clientService.findByEmail(email);
            if (client != null && client.getPassword() != null && passwordService.checkPassword(password, client.getPassword())) {
                session.setAttribute("user", client); // Store user in session

                Boolean purchaseIntent = (Boolean) session.getAttribute("purchaseIntent");
                if (Boolean.TRUE.equals(purchaseIntent)) {
                    session.removeAttribute("purchaseIntent"); // Remove purchase intent
                    return "redirect:/ticket/ticketNew"; // Redirect to ticket page
                }

                return "redirect:/home"; // Redirect to home page if no purchase intent
            } else {
                model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
                return "login"; // Return to login page with error message
            }
        } else {
            Admin admin = adminService.findById(email);
            if (admin != null && admin.getPassword() != null && passwordService.checkPassword(password, admin.getPassword())) {
                session.setAttribute("user", admin); // Store admin in session
                return "redirect:/adminPage"; // Redirect to admin page
            } else {
                model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
                return "login"; // Return to login page with error message
            }
        }
    }
}

