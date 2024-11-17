package uy.edu.um.proyectoTIC.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.*;

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
    private FilmRepository filmRepository;
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
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private BroadcastRepository broadcastRepository;
    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private passwordService passwordService;

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

    @Transactional
    public void deleteCinema(Long cinemaId)
    {
        cinemaRepository.deleteById(cinemaId);
    }

    public Broadcast createBroadcast(String dateTimeS, Integer broadcastPrice, Integer roomNbr, Integer centralId, Integer filmCode) throws EntityNotFoundException
    {
        return broadcastService.addBroadcast( dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);
    }

    @Transactional
    public void deleteBroadcast(Long broadcastId)
    {
        broadcastRepository.deleteById(broadcastId);
    }

    public Snack createSnack(String snackName, String snackPrice) throws DuplicateEntityException
    {
        if (Long.parseLong(snackPrice) < 1)
            throw new InvalidAttributeException("El precio debe ser mayor que 0");
        return snackService.addSnack(snackName,snackPrice);
    }

    @Transactional
    public void deleteSnackById(String snackName) throws EntityNotFoundException
    {
        snackRepository.deleteById(snackName);
    }

    //Quizas es necesario un trimm o algo para los nombres
    public Combo createCombo(Integer comboPrice, List<String> snackNames, String comboName) throws EntityNotFoundException
    {
        if (comboPrice < 1 || comboName == null || comboName.isEmpty())
            throw new InvalidAttributeException("Atributo no valido");

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
        return comboService.addCombo(comboPrice,snackList,comboName);
    }

    @Transactional
    public void deleteCombo(Long comboId)
    {
        comboRepository.deleteById(comboId);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);

        String hashedPassword = passwordService.hashPassword(password);

        Admin admin = Admin.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(birthDate)
                .password(hashedPassword)
                .build();

        return adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(String email)
    {
        adminRepository.deleteById(email);
    }

    public Room createRoom(Integer roomNbr, Integer centralId) throws EntityNotFoundException
    {
        return roomService.createRoom(roomNbr,centralId);
    }

    @Transactional
    public void deleteRoom(Integer roomId)
    {
        roomRepository.deleteById(roomId);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAllAdmins();
    }

    public Admin findByEmail(String email) throws EntityNotFoundException
    {
        Optional<Admin> client = adminRepository.findById(email);
        if(client.isEmpty())
            throw new EntityNotFoundException("No existe el administrador");
        Admin adminTemp = client.get();

        return adminTemp;
    }

    public Admin UpdateName(String email,String name) throws EntityNotFoundException
    {
        if (name == null)
            throw new InvalidIdException("El nombre no puede ser vacio");

        Admin admin = this.findByEmail(email);
        admin.setName(name);
        return adminRepository.save(admin);
    }

    public Admin UpdateAddress(String email,String address) throws EntityNotFoundException
    {
        if (address == null)
            throw new InvalidIdException("El nombre no puede ser vacio");

        Admin admin = this.findByEmail(email);
        admin.setAddress(address);
        return adminRepository.save(admin);
    }

    public Admin UpdateDate(String email,String date) throws EntityNotFoundException
    {
        if (date == null)
            throw new InvalidIdException("Fecha no valida");

        Admin admin = this.findByEmail(email);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(date, formatter);
        admin.setBirthdate(birthDate);
        return adminRepository.save(admin);
    }

    public Admin UpdatePassword(String email, String password) throws EntityNotFoundException
    {
        if (password == null || password.length() < 8)
            throw new InvalidIdException("Contraseña no apta");

        Admin admin = this.findByEmail(email);
        admin.setPassword(password);
        return adminRepository.save(admin);
    }

}
