package uy.edu.um.proyectoTIC.controllers;

import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/payment")
    public String showPaymentPage(Model model, HttpSession session) throws EntityNotFoundException {
        // Obtener el cliente desde la sesión
        Client client = (Client) session.getAttribute("user");

        model.addAttribute("client", client); // Agregar el cliente al modelo para pasarlo a la vista
        return "payment";
    }

    @PostMapping("/add-card")
    public String addCard(@RequestParam("card-number") Long cardNumber, HttpSession session) throws EntityNotFoundException {
        // Obtener el cliente desde la sesión
        Client client = (Client) session.getAttribute("user");

        // Actualizar el método de pago del cliente
        clientService.UpdatePaymentMethod(client.getEmail(), cardNumber);
        return "redirect:/payment"; // Redirigir a la página de pago después de agregar la tarjeta
    }
}
