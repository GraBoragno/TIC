package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.ComboRepository;
import uy.edu.um.proyectoTIC.repository.FilmRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SnackService snackService;
    @Autowired
    private ComboService comboService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private BroadcastRepository broadcastRepository;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private SnackRepository snackRepository;
    @Autowired
    private ComboRepository comboRepository;

    @GetMapping("/ticketNew")
    public String formulario(HttpSession session, @RequestParam("filmCode") Long filmCode, Model model)
    {
        Object user = session.getAttribute("user");
        if(user == null)
            return "redirect:/log-in";


        Optional<Film> filmAux = filmRepository.findById(filmCode);
        Film film = filmAux.get(); //Usar el Service

        List<Snack> snacks = snackService.getAvailableSnacks();
        List<Combo> combos = comboService.getAvailableCombos();
        List<Broadcast> broadcasts = broadcastService.getBroadcastsByFilmName(film.getFilmName()); //No funciona

        List<Long> centralIds = broadcasts.stream()
                .map(Broadcast::getCentralId)
                .distinct()
                .collect(Collectors.toList());

        List<Cinema> cinemas = cinemaService.findCinemasByCentralIds(centralIds);

        // Crear un Map de centralId a Cinema
        Map<Long, Cinema> cinemaMap = cinemas.stream()
                .collect(Collectors.toMap(Cinema::getCentralId, cinema -> cinema));

        model.addAttribute("film", film);
        model.addAttribute("broadcasts", broadcasts); // Cambio de "broadcast" a "broadcasts"
        model.addAttribute("cinemaMap", cinemaMap); // Cambio de "cinema" a "cinemaMap"
        model.addAttribute("snacks", snacks);
        model.addAttribute("combos", combos);

        return "ticketNew";
    }


    @PostMapping("/ticketNew")
    public String confirmBroadcast(HttpSession session,
                                 @RequestParam Long broadcastId,
                                 @RequestParam String snack,
                                 @RequestParam Long combos,
                                 @RequestParam double totalPrice) {


        Optional<Broadcast> brAux = broadcastRepository.findById(broadcastId);
        Optional<Snack> skAux = snackRepository.findById(snack);
        Optional<Combo> cbAux = comboRepository.findById(combos);
        Broadcast broadcast = brAux.get();
        Snack selectedSnack = skAux.get();
        Combo selectedCombo = cbAux.get();

        //Para usar despues
        session.setAttribute("broadcast", broadcast);
        session.setAttribute("selectedSnack", selectedSnack);
        session.setAttribute("selectedCombo", selectedCombo);
        session.setAttribute("totalPrice", totalPrice);

        return "redirect:/seats";
    }


//    @PostMapping("/ticketNew")
//    public String buyTicket(int broadcastId, int row, int column, String email, List<Combo> combos, List<Snack> snacks)
//    {
//        Ticket newTicket = ticketService.buyTicket(broadcastId, row, column, email, combos, snacks);
//        return "redirect:/log-in";
//    }
}