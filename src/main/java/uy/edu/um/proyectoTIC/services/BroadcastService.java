package uy.edu.um.proyectoTIC.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Room;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.FilmRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private FilmRepository filmRepository;


    // Verificar que no se agregue mas de una vez
    @Transactional
    public Broadcast addBroadcast(String dateTimeS, Integer broadcastPrice, Integer roomNbr, Integer centralId, Integer filmCode) throws EntityNotFoundException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeS, formatter);

        Optional<Cinema> cinemaAux = cinemaRepository.findById((long)centralId);
        if (cinemaAux.isEmpty()){
            throw new EntityNotFoundException("No existe el cine");
        }

        Cinema cinema = cinemaAux.get();

        int index = -1;
        for (int i = 0; i < cinema.getRoomsCollection().size(); i++) {
            if ((long)roomNbr == cinema.getRoomsCollection().get(i).getRoomNbr()){
                index = i;
            }
        }

        System.out.println(roomNbr);
        if (index == -1)
            throw new EntityNotFoundException("No existe la sala");

        System.out.println(index);
        Room room = cinema.getRoomsCollection().get(index);

        Optional<Film> filmAux = filmRepository.findById((long)filmCode);

        if (filmAux.isEmpty()){
            throw new EntityNotFoundException("No existe la pelicula");
        }
        Film film = filmAux.get();

        Broadcast newBroadcast = Broadcast.builder()
                .dateTime(dateTime)
                .broadcastPrice((long) broadcastPrice)
                .hasRoom(room)
                .centralId((long) centralId)
                .broadcastFilm(film)
                .build();

        return broadcastRepository.save(newBroadcast);

    }

    public List<Broadcast> getBroadcastsByFilmName(String filmName) {
        return broadcastRepository.findByFilmNameBroadcast(filmName, LocalDateTime.now());
    }

    public List<Broadcast> findByFilmAndCinema(String filmName, Long centralId) {
        return broadcastRepository.findByFilmCodeAndCentralId(filmName, centralId, LocalDateTime.now());
    }

    public List<Broadcast> findByFilmAndCinemaAndTime(Long filmCode, Long centralId, LocalDateTime dateTime) {
        return broadcastRepository.findByFilmCodeAndCentralIdAndDateTime(filmCode, centralId, dateTime);
    }

}