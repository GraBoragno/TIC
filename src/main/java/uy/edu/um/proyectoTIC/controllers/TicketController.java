package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.services.TicketService;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticketNew")
    public String formulario() {return "ticketNew";}


    @PostMapping("/ticketNew")
    public String chooseFunction(HttpSession Session, Model model, @RequestParam String neighborhood, @RequestParam String dateTime, @RequestParam List<Combo> combos, @RequestParam List<Snack> snacks) {
        model.addAttribute("neighborhood", neighborhood);
        model.addAttribute("dateTime", dateTime);
        model.addAttribute("combos", combos);
        model.addAttribute("snacks", snacks);
//        get broadcast id and add it
        return "seats";
    }



//    @PostMapping("/ticketNew")
//    public String buyTicket(int broadcastId, int row, int column, String email, List<Combo> combos, List<Snack> snacks)
//    {
//        Ticket newTicket = ticketService.buyTicket(broadcastId, row, column, email, combos, snacks);
//        return "redirect:/log-in";
//    }
}