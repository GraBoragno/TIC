package uy.edu.um.proyectoTIC.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.SeatRepository;
import uy.edu.um.proyectoTIC.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import uy.edu.um.proyectoTIC.services.SeatService;
import uy.edu.um.proyectoTIC.services.TicketService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatRepository seatRepository;


    @GetMapping("/paymentMethod")
    public String showPaymentPage(Model model, HttpSession session) throws EntityNotFoundException {
        Client client = (Client) session.getAttribute("user");
        Double totalPrice = (Double) session.getAttribute("totalPrice");

        if (client == null) {
            return "redirect:/log-in";
        }

        model.addAttribute("user", client);
        model.addAttribute("totalPrice", totalPrice);
        return "paymentMethod";
    }

    @PostMapping("/add-card")
    public String addCard(@RequestParam("card-number") Long cardNumber, HttpSession session) throws EntityNotFoundException {
        Client client = (Client) session.getAttribute("user");

        clientService.UpdatePaymentMethod(client.getEmail(), cardNumber);

        Client updatedClient = clientService.findByEmail(client.getEmail());
        session.setAttribute("user", updatedClient);
        return "redirect:/payment/paymentMethod";
    }

    @PostMapping("/payWithSavedCard")
    public String payWithSavedCard(HttpSession session) throws EntityNotFoundException
    {
        Client client = (Client) session.getAttribute("user");
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        Broadcast broadcast = (Broadcast) session.getAttribute("broadcast");
        Snack snack = (Snack) session.getAttribute("selectedSnack");
        Combo combo = (Combo) session.getAttribute("selectedCombo");
        Long selectedSeatId = (Long) session.getAttribute("selectedSeat");

        Seat selectedSeat = seatRepository.findById(selectedSeatId).get();
        int row = selectedSeat.getSeatRow().intValue();
        int column = selectedSeat.getSeatColumn().intValue();

        List<Snack> selectedSnacks = new ArrayList<>();
        if (snack != null)
            selectedSnacks.add(snack);

        List<Combo> selectedCombo = new ArrayList<>();
        if (combo != null)
            selectedCombo.add(combo);


        Ticket newTicket = ticketService.buyTicket(broadcast.getBroadcastId().intValue(),row,column,client.getEmail(),selectedCombo,selectedSnacks);


        return "redirect:/successfulPurchase";
    }
}
