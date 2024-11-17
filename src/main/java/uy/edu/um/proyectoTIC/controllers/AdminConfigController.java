package uy.edu.um.proyectoTIC.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class AdminConfigController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/adminConfig")
    public String showAdminConfigPage(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");

        model.addAttribute("user", admin);
        return "adminConfig";
    }

    @PostMapping("/adminConfig")
    public String updateClient(@RequestParam String name, @RequestParam String address, @RequestParam String date, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) throws EntityNotFoundException {

        Admin admin = (Admin) session.getAttribute("user");

        try {
            adminService.UpdateName(admin.getEmail(), name);
            adminService.UpdateAddress(admin.getEmail(), address);

            if (!date.isEmpty()) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
                String formattedDate = parsedDate.format(outputFormatter);

                adminService.UpdateDate(admin.getEmail(), formattedDate);
            }

            if (!password.isEmpty()) {
                adminService.UpdatePassword(admin.getEmail(), password);
            }

            Admin updatedUser = adminService.findByEmail(admin.getEmail());
            session.setAttribute("user", updatedUser);

            redirectAttributes.addFlashAttribute("successMessage", "Los cambios se han guardado correctamente.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ocurrió un error al actualizar la información: " + e.getMessage());
        }


        return "redirect:/adminConfig";
    }
}
