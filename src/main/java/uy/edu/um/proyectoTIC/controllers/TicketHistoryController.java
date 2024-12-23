package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.CinemaService;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;
import uy.edu.um.proyectoTIC.services.TicketService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketHistoryController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private FilmService filmService;


    @GetMapping("/ticketHistory")
    public String history(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("user");
        List<Ticket> tickets;

        tickets = clientService.getTicketByEmail(client.getEmail());
        System.out.println(tickets.size());
        List<Broadcast> broadcasts = new ArrayList<>();
        for (Ticket ticket : tickets) {
            broadcasts.add(ticket.getTicketBroadcast());
        }


        LocalDateTime dateTime = LocalDateTime.now();
        model.addAttribute("dateTime", dateTime);
        model.addAttribute("broadcasts", broadcasts);
        model.addAttribute("tickets", tickets);
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);

        return "ticketHistory";
    }

    @PostMapping("/cancelTicket")
    public String cancelTicket(HttpSession session, @RequestParam Long ticketId) throws EntityNotFoundException {
        ticketService.deleteTicket(ticketId);
        return "redirect:/ticketHistory";
    }

    @PostMapping("/rateFilm")
    public String calificarPelicula(HttpSession session, @RequestParam Float rating, @RequestParam Integer ticketId) throws EntityNotFoundException {

        Ticket ticket = ticketService.findTicketById(ticketId.longValue()).get();
        filmService.rateFilm(ticket.getTicketBroadcast().getBroadcastFilm().getFilmCode().intValue(),rating);
        return "redirect:/ticketHistory";
    }

}


