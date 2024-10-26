package uy.edu.um.proyectoTIC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;


    @Autowired
    private FilmService filmService;

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("movies", filmService.getAvailableFilms());
        return "home";
    }
}


