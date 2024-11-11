package uy.edu.um.proyectoTIC.controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private SnackService snackService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FilmService filmService;


    @GetMapping("/adminPage")
    public String admin(Model model , HttpSession session) {

        List<Snack> snacks = snackService.getAvailableSnacks();
        List<Film> films = filmService.getRatedFilms();
        model.addAttribute("films", films);
        model.addAttribute("snacks", snacks);
        return "adminPage";
    }


    // Snacks
    @PostMapping("/adminAddSnack")
    public String addSnack(@RequestParam String snackName, @RequestParam String snackPrice, RedirectAttributes redirectAttributes) throws DuplicateEntityException {
        try {
            adminService.createSnack(snackName, snackPrice);
            redirectAttributes.addFlashAttribute("okMessage", "Snack agregado");
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "adminPage";
    }

    @PostMapping("/adminDeleteSnack")
public String deleteSnack(@RequestParam String snackName, RedirectAttributes redirectAttributes) {
    Snack snack = SnackRepository.findById(snackName).orElse(null);
    if (snack != null) {
        adminService.deleteSnack(snack); // Llamada al servicio con el objeto completo
        redirectAttributes.addFlashAttribute("okMessage", "Snack eliminado");
    } else {
        redirectAttributes.addFlashAttribute("errorMessage", "Snack no encontrado");
    }
    return "adminPage";
}

//    @PostMapping("/adminDeleteSnack")
//    public String deleteSnack(@RequestParam String snackName, RedirectAttributes redirectAttributes) {
//        try {
//            adminService.deleteSnack(snackName);
//            redirectAttributes.addFlashAttribute("okMessage", "Snack eliminado");
//        } catch (EntityNotFoundException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//        }
//        return "adminPage";
//    }

    // Films
    @PostMapping("/adminCreateFilm")
    public String addFilm(@RequestParam String filmName, @RequestParam String directorName, @RequestParam Integer duration, @RequestParam String releaseYearDate, @RequestParam String genres, RedirectAttributes redirectAttributes) {
        try {
            adminService.createFilm(filmName, directorName, duration, releaseYearDate, genres);
            redirectAttributes.addFlashAttribute("okMessage", "Pelicula agregada");
        } catch (DuplicateEntityException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage()); // tira el error correspondiente
        }
        return "adminPage";
    }

        // deleteFilm al final no

    // Cinemas
    @PostMapping("/adminCreateCinema")
    public String addCinema(@RequestParam Integer  centralId, @RequestParam Integer roomQty, @RequestParam String neighborhood, RedirectAttributes redirectAttributes) {
        try {
            adminService.createCinema(centralId, roomQty, neighborhood);
            redirectAttributes.addFlashAttribute("okMessage", "Cine agregado");
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "adminPage";
    }

//    @PostMapping("/adminDeleteCinema")
//    public String deleteCinema(@RequestParam Long centralId, RedirectAttributes redirectAttributes) {
//        try {
//            adminService.deleteCinema(centralId);
//            redirectAttributes.addFlashAttribute("successMessage", "Cine eliminado");
//        } catch (EntityNotFoundException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "El cine no existe.");
//        }
//        return "adminPage";
//    }


    // Rooms
    @PostMapping("/adminAddRoom")
    public String addRoom(@RequestParam Integer roomNbr, @RequestParam Integer centralId,
                          RedirectAttributes redirectAttributes) {
        try {
            adminService.createRoom(roomNbr, centralId);
            redirectAttributes.addFlashAttribute("successMessage", "Sala agregada");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "adminPage";
    }

//    @PostMapping("/adminDeleteRoom")
//    public String deleteRoom(@RequestParam Long roomId, @RequestParam Long centralId, RedirectAttributes redirectAttributes) {
//        try {
//            adminService.deleteRoom(roomId, centralId);
//            redirectAttributes.addFlashAttribute("success", "Sala eliminada");
//        } catch (EntityNotFoundException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/admin/rooms";
//    }

    // Combos
    @PostMapping("/adminAddCombo")
    public String addCombo(@RequestParam Integer comboPrice, @RequestParam List<String> snacks,
                           @RequestParam String comboName, RedirectAttributes redirectAttributes) {
        try {
            adminService.createCombo(comboPrice, snacks, comboName);
            redirectAttributes.addFlashAttribute("success", "Combo agregado");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "adminPage";
    }

//    @PostMapping("/deleteCombo")
//    public String deleteCombo(@RequestParam Long comboId, RedirectAttributes redirectAttributes) {
//        try {
//            adminService.deleteCombo(comboId);
//            redirectAttributes.addFlashAttribute("success", "Combo eliminado");
//        } catch (EntityNotFoundException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "adminPage";
//    }

}

// Broadcast





