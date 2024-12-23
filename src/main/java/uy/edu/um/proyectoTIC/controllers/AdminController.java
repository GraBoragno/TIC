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
import uy.edu.um.proyectoTIC.exceptions.*;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.RoomRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.util.ArrayList;
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
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ComboService comboService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private BroadcastRepository broadcastRepository;


    @GetMapping("/adminPage")
    public String admin(Model model) {

        List<Snack> snacks = snackService.getAvailableSnacks();
        List<Film> films = filmService.getRatedFilms();
        List<Room> rooms = roomRepository.findAll();
        List<Combo> combos = comboService.getAvailableCombos();
        List<Cinema> cinemas = cinemaRepository.findAll();
        List<Broadcast> broadcasts = broadcastRepository.findAll();
        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("films", films);
        model.addAttribute("snacks", snacks);
        model.addAttribute("rooms", rooms);
        model.addAttribute("combos", combos);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("broadcasts", broadcasts);
        model.addAttribute("admins", admins);
        return "adminPage";
    }


    // Snacks
    @PostMapping("/adminAddSnack")
    public String addSnack(@RequestParam String snackName, @RequestParam String snackPrice, RedirectAttributes redirectAttributes) throws DuplicateEntityException
    {
        try {
            adminService.createSnack(snackName, snackPrice);
            redirectAttributes.addFlashAttribute("okMessage", "Snack agregado");
        } catch (DuplicateEntityException | InvalidIdException | InvalidAttributeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adminPage";
    }


    @PostMapping("/adminDeleteSnack")
    public String deleteSnack(@RequestParam String snackName, RedirectAttributes redirectAttributes)
    {
        try {
            adminService.deleteSnackById(snackName);
            redirectAttributes.addFlashAttribute("okMessage", "Snack eliminado");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adminPage";
    }

    // Films
    @PostMapping("/adminCreateFilm")
    public String addFilm(@RequestParam String filmName, @RequestParam String directorName, @RequestParam Integer duration, @RequestParam String releaseYearDate, @RequestParam String genres, RedirectAttributes redirectAttributes)
    {
        try {
            adminService.createFilm(filmName, directorName, duration, releaseYearDate, genres);
            redirectAttributes.addFlashAttribute("okMessage", "Pelicula agregada");
        } catch (DuplicateEntityException | EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adminPage";
    }

    // Cinemas
    @PostMapping("/adminCreateCinema")
    public String addCinema(@RequestParam Integer centralIdNewCinema, @RequestParam Integer roomQty, @RequestParam String neighborhood, RedirectAttributes redirectAttributes)
    {
        try {
            adminService.createCinema(centralIdNewCinema, roomQty, neighborhood);
            redirectAttributes.addFlashAttribute("okMessage", "Cine agregado");
        } catch (DuplicateEntityException | InvalidIdException |InvalidRoomQtyException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adminPage";
    }

    @PostMapping("/adminDeleteCinema")
    public String deleteCinema(@RequestParam Long cinemaId, RedirectAttributes redirectAttributes)
    {
        try {
            adminService.deleteCinema(cinemaId);
            redirectAttributes.addFlashAttribute("okMessage", "Cine eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el cine: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }

    // Rooms
    @PostMapping("/adminAddRoom")
    public String addRoom(@RequestParam Integer roomNbrRoom, @RequestParam Integer centralIdRoom, RedirectAttributes redirectAttributes)
    {
        try {
            adminService.createRoom(roomNbrRoom, centralIdRoom);
            redirectAttributes.addFlashAttribute("okMessage", "Sala agregada");
        } catch (EntityNotFoundException | InvalidRoomQtyException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/adminPage";
    }

    @PostMapping("/adminDeleteRoom")
    public String deleteRoom(@RequestParam Integer roomId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteRoom(roomId);
            redirectAttributes.addFlashAttribute("okMessage", "Sala eliminada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar la sala: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }

    // Combos
    @PostMapping("/adminAddCombo")
    public String addCombo(@RequestParam Integer comboPrice, @RequestParam List<Integer> snackQuantities, @RequestParam String comboName, RedirectAttributes redirectAttributes)
    {

            List<String> selectedSnacks = new ArrayList<>();

            try {

                List<Snack> allSnacks = snackService.getAvailableSnacks();


                for (int i = 0; i < allSnacks.size(); i++) {
                    String snack = allSnacks.get(i).getSnackName();
                    int quantity = snackQuantities.get(i);


                    for (int j = 0; j < quantity; j++) {
                        selectedSnacks.add(snack);
                    }
                }

                adminService.createCombo(comboPrice, selectedSnacks, comboName);
                redirectAttributes.addFlashAttribute("okMessage", "Combo agregado");
            } catch (EntityNotFoundException | InvalidAttributeException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            }

            return "redirect:/adminPage";
        }

    @PostMapping("/adminDeleteCombo")
    public String deleteCombo(@RequestParam Long comboId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteCombo(comboId);
            redirectAttributes.addFlashAttribute("okMessage", "Combo eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el combo: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }


    // Broadcasts
    @PostMapping("/adminAddBroadcast")
    public String addBroadcast(@RequestParam String dateTimeS, @RequestParam Integer broadcastPrice, @RequestParam Integer roomNbrBroadcast, @RequestParam Integer centralIdBroadcast, @RequestParam Integer filmCode, RedirectAttributes redirectAttributes) {
        try {
            adminService.createBroadcast(dateTimeS, broadcastPrice, roomNbrBroadcast, centralIdBroadcast, filmCode);
            redirectAttributes.addFlashAttribute("okMessage", "Función creada correctamente.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al crear la función: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }

    @PostMapping("/adminDeleteBroadcast")
    public String deleteBroadcast(@RequestParam Long broadcastId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteBroadcast(broadcastId);

            redirectAttributes.addFlashAttribute("okMessage", "Función eliminada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar la función: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }


    // Admins
    @PostMapping("/adminAddAdmin")
    public String addAdmin(@RequestParam String email, @RequestParam String name, @RequestParam String address, @RequestParam String birthdate, @RequestParam String password, RedirectAttributes redirectAttributes) {


        try {
            adminService.createAdmin(email, name, address, birthdate, password);
            redirectAttributes.addFlashAttribute("okMessage", "Administrador creado correctamente.");
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Ya existe un administrador con ese email.");
        } catch (InvalidIdException | InvalidAttributeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error en los datos: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }

    @PostMapping("/adminDeleteAdmin")
    public String deleteAdmin(@RequestParam String adminEmail, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAdmin(adminEmail);
            redirectAttributes.addFlashAttribute("okMessage", "Administrador eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el administrador: " + e.getMessage());
        }
        return "redirect:/adminPage";
    }
}



