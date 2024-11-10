package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import uy.edu.um.proyectoTIC.entities.Seat;
import uy.edu.um.proyectoTIC.services.BroadcastService;
import uy.edu.um.proyectoTIC.services.FilmService;
import uy.edu.um.proyectoTIC.services.SeatService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SeatController {


    @Autowired
    private FilmService filmService;

    @Autowired
    private SeatService seatService;
    @Autowired
    private BroadcastService broadcastService;

    @Transactional
    @GetMapping("/seats")
    public String seats(HttpSession session, Model model)
    {
        Broadcast broadcast = (Broadcast) session.getAttribute("broadcast");
        List<Long> notAvailableSeats = broadcastService.getAssignedSeats(broadcast.getBroadcastId());

        List<Seat> allSeats = seatService.getAllSeats();

        // Agrupar los asientos en filas (suponiendo que cada fila tiene 10 asientos)
        List<List<Seat>> seatsByRow = new ArrayList<>();
        for (int i = 0; i < allSeats.size(); i += 10) {
            seatsByRow.add(allSeats.subList(i, Math.min(i + 10, allSeats.size())));
        }

        model.addAttribute("seatsByRow", seatsByRow);
        model.addAttribute("notAvailableSeats", notAvailableSeats);
        Long selectedSeatId = (Long) session.getAttribute("selectedSeat");
        model.addAttribute("selectedSeatId", selectedSeatId);  // Puede ser null si no hay ningún asiento seleccionado

        return "seats";
    }

    @PostMapping("/selectSeat")
    public String selectSeat(HttpSession session, @RequestParam Long seatId)
    {
        // Guardar el asiento seleccionado en la sesión
        session.setAttribute("selectedSeat", seatId);

        // Redirigir a la página de asientos (o donde quieras después de confirmar)
        return "redirect:/seats";  // O a la página que desees
    }


    @PostMapping("/confirmSeat")
    public String confirmSeat(HttpSession session)
    {
        return "redirect:/payment/paymentMethod";
    }


}