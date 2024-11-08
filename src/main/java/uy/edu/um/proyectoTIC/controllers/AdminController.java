package uy.edu.um.proyectoTIC.controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.AdminService;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;
import uy.edu.um.proyectoTIC.services.SnackService;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private SnackService snackService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FilmService filmService;


    @GetMapping("/adminPage")
    public String admin(Model model , HttpSession session) {

        List<Snack> snacks = snackService.getAvailableSnacks();
        List<Film> films = filmService.getRatedFilms();
        model.addAttribute("films", films);
        model.addAttribute("snacks", snacks);
        return "adminPage";
    }



    @PostMapping("/adminAddSnack")
    public String addSnack(@RequestParam String snackName, @RequestParam String snackPrice) throws DuplicateEntityException {
        Snack newSnack = snackService.addSnack(snackName, snackPrice);
        return "adminPage";

    }

    @PostMapping("/adminDeleteSnack")
    public String deleteSnack(@RequestParam String snackName) {
//        snack = snackService.getSnack(snackName);
//        adminService.deleteSnack(snack);
        return "adminPage";
    }

}


