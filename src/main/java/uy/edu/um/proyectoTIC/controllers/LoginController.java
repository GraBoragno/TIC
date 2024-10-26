package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uy.edu.um.proyectoTIC.services.ClientService;

@Controller
public class LoginController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/log-in")
    public String login(Model model) {
        return "login";
    }
}