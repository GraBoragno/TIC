package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;


@Controller
@RequestMapping("/register")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/registerNew")
    public String formulario() {return "registerNew";}


    @PostMapping("/registerNew")
    public String addClient(@RequestParam String email, @RequestParam String name, @RequestParam String address, @RequestParam String birthdate,@RequestParam String password)
    {
        Client newClient = clientService.addClient(email, name, address, birthdate, password);
        return "redirect:/log-in";
    }
}