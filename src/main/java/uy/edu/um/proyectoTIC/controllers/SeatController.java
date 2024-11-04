package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

@Controller
public class SeatController {


    @Autowired
    private FilmService filmService;

    @GetMapping("/seats")
    public String seats() {

        return "seats";
    }

//    @PostMapping("/chooseSeats")
//    public String chooseSeats(Model model, @RequestParam Long seatRow, @RequestParam Long seatColumn ,  @ModelAttribute(re)) {
//        model.addAttribute("seatRow", seatRow);
//        model.addAttribute("seatColumn", seatColumn);
////        buyTicket(int broadcastId, int row, int column, String email, List<Combo> combos, List<Snack> snacks
//        return "ticketBuy";
//    }
}