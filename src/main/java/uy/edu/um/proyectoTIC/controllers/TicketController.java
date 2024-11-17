package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        Film film = filmAux.get();

        List<Snack> snacks = snackService.getAvailableSnacks();
        List<Combo> combos = comboService.getAvailableCombos();
        List<Broadcast> broadcasts = broadcastService.getBroadcastsByFilmName(film.getFilmName());

        List<Long> centralIds = broadcasts.stream()
                .map(Broadcast::getCentralId)
                .distinct()
                .collect(Collectors.toList());

        List<Cinema> cinemas = cinemaService.findCinemasByCentralIds(centralIds);


        Map<Long, Cinema> cinemaMap = cinemas.stream()
                .collect(Collectors.toMap(Cinema::getCentralId, cinema -> cinema));

        model.addAttribute("film", film);
        model.addAttribute("broadcasts", broadcasts);
        model.addAttribute("cinemaMap", cinemaMap);
        model.addAttribute("snacks", snacks);
        model.addAttribute("combos", combos);
        model.addAttribute("filmCode", filmCode);

        return "ticketNew";
    }


    @PostMapping("/ticketNew")
    public String confirmBroadcast(HttpSession session, @RequestParam(required = false) Long broadcastId, @RequestParam(required = false) String snack, @RequestParam(required = false) String combos, @RequestParam double totalPrice) {



        Optional<Broadcast> brAux = broadcastRepository.findById(broadcastId);
        Snack selectedSnack = ("none".equals(snack)) ? null : snackRepository.findById(snack).orElse(null);
        Combo selectedCombo = ("none".equals(combos)) ? null : comboRepository.findById(Long.valueOf(combos)).orElse(null);

        Broadcast broadcast = brAux.orElse(null);

        //Para usar despues
        session.setAttribute("broadcast", broadcast);
        session.setAttribute("selectedSnack", selectedSnack);
        session.setAttribute("selectedCombo", selectedCombo);
        session.setAttribute("totalPrice", totalPrice);

        return "redirect:/seats";
    }


}