package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ClientService clientService;


    @Autowired
    private FilmService filmService;



    @GetMapping("/home")
    public String home(Model model, HttpSession session)
    {
        Object user = session.getAttribute("user");

        if (user instanceof Admin)
            return "redirect:/adminPage";


        List<Film> films = filmService.getAvailableFilmsByDate();

        // Divide las películas en grupos de 6
        List<List<Film>> filmGroups = new ArrayList<>();
        for (int i = 0; i < films.size(); i += 6) {
            filmGroups.add(films.subList(i, Math.min(i + 6, films.size())));
        }

        model.addAttribute("filmGroups", filmGroups);
        model.addAttribute("user", user);

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


