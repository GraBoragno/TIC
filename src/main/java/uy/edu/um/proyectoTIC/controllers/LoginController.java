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

@Controller
public class LoginController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/log-in")
    public String showLoginForm() {
        return "login"; // Muestra el formulario de login
    }

    @PostMapping("/log-in")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) throws EntityNotFoundException
    {
        if (!email.endsWith("@wtf.com")) {
            Client client = clientService.findByEmail(email);
            if (client != null && client.getPassword() != null && client.getPassword().equals(password)) {

                session.setAttribute("user", client); // Guarda el usuario en la sesión

                Boolean purchaseIntent = (Boolean) session.getAttribute("purchaseIntent");
                if (Boolean.TRUE.equals(purchaseIntent)) {
                    session.removeAttribute("purchaseIntent"); // Eliminar la intención de compra
                    return "redirect:/ticket/ticketNew"; // Redirigir a la primera página del flujo de compra
                }
                return "redirect:/home"; // Redirigir a home si no hay intención de compra
            } else {
                return "redirect:/log-in?error=true"; // Redirige a login con mensaje de error
            }
        }
        else if (email.endsWith("@wtf.com")) {
            Admin admin = adminService.findById(email);
            if (admin != null && admin.getPassword() != null && admin.getPassword().equals(password)){

                session.setAttribute("user", admin);

                return "redirect:/adminPage";
            } else {
                return "redirect:/log-in?error=true";
            }
        }
        return "redirect:/log-in?error=true";
    }

}

