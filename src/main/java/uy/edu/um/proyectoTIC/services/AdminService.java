package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.AdminRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private FilmService filmService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private SnackService snackService;
    @Autowired
    private ComboService comboService;
    @Autowired
    private SnackRepository snackRepository;

    public Admin findById(String email) throws EntityNotFoundException
    {
        Optional<Admin> admin = adminRepository.findById(email);
        if (admin.isEmpty())
            throw new EntityNotFoundException("No existe el administrador");

        return admin.get();
    }

    public Film createFilm(String filmName, String directorName, Integer duration, String releaseYearDate, String genres) throws DuplicateEntityException, EntityNotFoundException
    {
        return filmService.addFilm(filmName, directorName, duration, releaseYearDate, genres);
    }

    public Cinema createCinema(Integer centralId, Integer roomQty, String neighborhood) throws DuplicateEntityException
    {
        return cinemaService.addCinema(centralId, roomQty, neighborhood);
    }

    public Broadcast createBroadcast(String dateTimeS, Integer broadcastPrice, Integer roomNbr, Integer centralId, Integer filmCode) throws EntityNotFoundException
    {
        return broadcastService.addBroadcast( dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);
    }

    public Snack createSnack(String snackName, String snackPrice) throws DuplicateEntityException
    {
        if (Long.parseLong(snackPrice) < 1)
            throw new InvalidAttributeException("El precio debe ser mayor que 0");
        return snackService.addSnack(snackName,snackPrice);
    }

    //Quizas es necesario un trimm o algo para los nombres
    public Combo createCombo(Integer comboPrice, List<String> snackNames) throws EntityNotFoundException
    {
        if (comboPrice < 1)
            throw new InvalidAttributeException("El precio debe ser mayor que 0");

        List<Snack> snackList = new ArrayList<>();

        for(String snackName : snackNames){
            Optional<Snack> snackAux = snackRepository.findById(snackName);
            if (snackAux.isPresent()) {
                Snack addedSnack = snackAux.get();
                snackList.add(addedSnack);
            }else {
                throw new EntityNotFoundException("No se encontro: " + snackName);
            }
        }
        return comboService.addCombo(comboPrice,snackList);
    }

    public Admin createAdmin(String email, String name, String address, String birthdate, String password) throws DuplicateEntityException
    {
        if (!email.endsWith("@wtf.com"))
            throw new InvalidIdException("El email del administrador debe terminar en \"@wtf.com\"");

        if (name == null || name.isEmpty() || address == null || birthdate == null || password == null || password.length() < 8){
            throw new InvalidAttributeException("Atributo no valido");
        }

        if (adminRepository.findById(email).isPresent()){
            throw new DuplicateEntityException("Ya existe un administrador con ese email");
        }

        //Cambiar el birthdate a String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);

        Admin admin = Admin.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(birthDate)
                .password(password)
                .build();

        return adminRepository.save(admin);
    }

    //Agregar delete Methods
}
