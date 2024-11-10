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
public class ClientConfigController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clientConfig")
    public String showClientConfig(Model model, HttpSession session) throws EntityNotFoundException {


        Client client = (Client) session.getAttribute("user");

        model.addAttribute("client", client); // Agregar el cliente al modelo para pasarlo a la vista
        return "clientConfig"; // Retorna la vista clientConfig.html
    }

    @PostMapping("/clientConfig")
    public String updateClient(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String date,
            @RequestParam String password,
            @RequestParam Long cardNbr,
            HttpSession session) throws EntityNotFoundException {

        String email = (String) session.getAttribute("userEmail");

        // Actualiza los datos del cliente
        clientService.UpdateName(email, name);
        clientService.UpdateAddress(email, address);

        if (!date.isEmpty()) {
            clientService.UpdateDate(email, date); // Solo actualizar si se proporciona una nueva fecha
        }

        if (!password.isEmpty()) {
            clientService.UpdatePassword(email, password); // Solo actualizar si se proporciona una nueva contraseña
        }

        if (cardNbr != null) {
            clientService.UpdatePaymentMethod(email, cardNbr); // Actualizar método de pago si se proporciona
        }
        return "redirect:/clientConfig"; // Redirigir a la página de configuración después de actualizar
    }
}