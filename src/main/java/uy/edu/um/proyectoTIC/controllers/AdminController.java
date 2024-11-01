package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

@Controller
public class AdminController {

    @GetMapping("/adminPage")
    public String admin() {
        return "adminPage";
    }

//    @PostMapping("/test")
//    public String test(Model model, HttpSession session) throws EntityNotFoundException
//    {
//        Client user = (Client) session.getAttribute("user");
//        if (!isAdmin(user.getEmail())){
//            return "redirect:/home";
//        }
//        return "redirect:/adminPage";
//    }

}


