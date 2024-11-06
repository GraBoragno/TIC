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
        // Obtener el email del cliente desde la sesión
        String email = (String) session.getAttribute("userEmail");

        // Asumimos que el usuario ya está autenticado y ha pasado por las páginas anteriores
        Client client = clientService.findByEmail(email); // Buscar al cliente por su email

        model.addAttribute("client", client); // Agregar el cliente al modelo para pasarlo a la vista
        return "payment"; // Retorna el nombre de la plantilla Thymeleaf (payment.html)
    }

    @PostMapping("/add-card")
    public String addCard(@RequestParam("card-number") Long cardNumber, HttpSession session) throws EntityNotFoundException {
        // Obtener el email del cliente desde la sesión
        String email = (String) session.getAttribute("userEmail");

        // Actualiza el método de pago del cliente
        clientService.UpdatePaymentMethod(email, cardNumber);
        return "redirect:/payment"; // Redirigir a la página de pago después de agregar la tarjeta
    }
}