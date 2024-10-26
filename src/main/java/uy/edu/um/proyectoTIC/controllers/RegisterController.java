package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;


@Controller
public class RegisterController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/register/new")
    public ResponseEntity<Client> addClient(@RequestParam String email, @RequestParam String name, @RequestParam String address, @RequestParam String birthdate,@RequestParam String password)
    {
        Client newClient= clientService.addClient(email, name, address, birthdate, password);
        return ResponseEntity.ok(newClient);
    }
}