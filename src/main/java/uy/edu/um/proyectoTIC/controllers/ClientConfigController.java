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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class ClientConfigController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clientConfig")
    public String showClientConfig(Model model, HttpSession session) throws EntityNotFoundException {


        Client client = (Client) session.getAttribute("user");

        model.addAttribute("user", client);
        return "clientConfig";
    }

    @PostMapping("/clientConfig")
    public String updateClient(@RequestParam String name, @RequestParam String address, @RequestParam String date, @RequestParam String password, @RequestParam(required = false) Long cardNbr, HttpSession session, Model model) throws EntityNotFoundException {

        Client client = (Client) session.getAttribute("user");

        clientService.UpdateName(client.getEmail(), name);
        clientService.UpdateAddress(client.getEmail(), address);

        if (!date.isEmpty()) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            String formattedDate = parsedDate.format(outputFormatter);

            clientService.UpdateDate(client.getEmail(), formattedDate);
        }

        if (!password.isEmpty()) {
            clientService.UpdatePassword(client.getEmail(), password);
        }

        if (cardNbr != null) {
            clientService.UpdatePaymentMethod(client.getEmail(), cardNbr);
        }

        Client udatedClient = clientService.findByEmail(client.getEmail());
        session.setAttribute("user", udatedClient);

        return "redirect:/clientConfig";
    }
}