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
        return "login";
    }

    @PostMapping("/log-in")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model) throws EntityNotFoundException {


        if (!email.endsWith("@wtf.com")) {
            Client client;
            try {
                client = clientService.findByEmail(email);
            } catch (EntityNotFoundException e) {
                model.addAttribute("errorEmail", e.getMessage());
                return "login";
            }


            try {
            if (client != null && client.getPassword() != null && passwordService.checkPassword(password, client.getPassword())) {
                session.setAttribute("user", client); // Store user in session

                Boolean purchaseIntent = (Boolean) session.getAttribute("purchaseIntent");
                if (Boolean.TRUE.equals(purchaseIntent)) {
                    session.removeAttribute("purchaseIntent");
                    return "redirect:/ticket/ticketNew";
                }

                return "redirect:/home";

            } else {
                model.addAttribute("errorPassword", "contraseña incorrecta.");
                return "login";
            }
            }catch (Exception e) {
                model.addAttribute("errorPassword", "contraseña incorrecta.");
                return "login";
            }
        } else {
            Admin admin;
            try {
            admin = adminService.findById(email);
            }
            catch (EntityNotFoundException e) {
                model.addAttribute("errorEmail", e.getMessage());
                return "login";
            }
            try {

                if (admin != null && admin.getPassword() != null && passwordService.checkPassword(password, admin.getPassword())) {
                    session.setAttribute("user", admin);
                    return "redirect:/adminPage";
                } else {
                    model.addAttribute("errorPassword", "contraseña incorrecta.");
                    return "login";
                }
            }catch (Exception e) {
                model.addAttribute("errorPassword", "contraseña incorrecta.");
                return "login";
            }

        }
    }
}

