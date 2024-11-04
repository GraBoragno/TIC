package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;


    @Autowired
    private FilmService filmService;


    //Cambiar la parte del Admin
    @GetMapping("/home")
    public String home(Model model, HttpSession session)
    {
        Client user = (Client) session.getAttribute("user");
        model.addAttribute("movies", filmService.getAvailableFilms());
        model.addAttribute("user", user);// Añade el usuario al modelo si esta en sesión
        if (user != null) {
            model.addAttribute("isAdmin", isAdmin(user.getEmail()));
        }
        return "home";
    }

    @PostMapping("/log-out")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/log-in";
    }

    private boolean isAdmin(String email)
    {
        return email.endsWith("@wtf.com");
    }
}


