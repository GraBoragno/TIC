package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.services.ClientService;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/registerNew")
    public String formulario() {return "registerNew";}


    @PostMapping("/registerNew")
    public String addClient(@RequestParam String email, @RequestParam String name, @RequestParam String address, @RequestParam String birthdate, @RequestParam String password , RedirectAttributes redirectAttributes)
    {
        if (email.endsWith("@wtf.com"))
            return "registerNew";

        Client newClient = clientService.addClient(email, name, address, birthdate, password);
        return "redirect:/log-in";
    }
}