package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import uy.edu.um.proyectoTIC.entities.Seat;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.FilmService;

@Controller
public class SeatController {


    @Autowired
    private FilmService filmService;

    @GetMapping("/seats")
    public String seats(HttpSession session, Model model) {

        return "seats";
    }

    @PostMapping("/selectSeat")
    public String selectSeat(@RequestParam String seatId) {
        System.out.println(seatId);
        return "redirect:/payment";
    }
//    @PostMapping("/chooseSeats")
//    public String chooseSeats(Model model, @RequestParam Long seatRow, @RequestParam Long seatColumn ,  @ModelAttribute(re)) {
//        model.addAttribute("seatRow", seatRow);
//        model.addAttribute("seatColumn", seatColumn);
////        buyTicket(int broadcastId, int row, int column, String email, List<Combo> combos, List<Snack> snacks
//        return "ticketBuy";
//    }
}