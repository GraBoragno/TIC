package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uy.edu.um.proyectoTIC.services.ClientService;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/home") // Map this method to the root URL
    public String home(Model model) {
        // You can retrieve data from the service and add it to the model
       // Assuming you have a method to get all clients
        return "home"; // Return the name of the Thymeleaf template (home.html)
    }
}


