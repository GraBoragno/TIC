package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Ticket;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketHistoryController {

    @Autowired
    private ClientService clientService;





    @GetMapping("/ticketHistory")
    public String history(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("user");
//        List<Ticket> tickets;
//        try {
//            tickets = clientService.getTicketByEmail(client.getEmail());
////           List<Ticket> tickets = client.getTicketsBought();
//        } catch (EntityNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        model.addAttribute("tickets", tickets);
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
//
        return "ticketHistory";
    }

//    @PostMapping("/log-out")
//    public String logout(HttpSession session)
//    {
//        session.invalidate();
//        return "redirect:/log-in";
//    }
}


