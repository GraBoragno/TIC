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
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.FilmRepository;
import uy.edu.um.proyectoTIC.repository.RoomRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private RoomRepository roomRepository;


    @Transactional
    public Long addBroadcastAux(Integer roomNbr, Integer centralId)
    {
        return (roomRepository.findRoomIdByCentralIdAndRoomNbr((long)centralId, roomNbr));
    }


    @Transactional
    public Broadcast addBroadcast(String dateTimeS, Integer broadcastPrice, Integer roomNbr, Integer centralId, Integer filmCode) throws EntityNotFoundException
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeS, formatter);

        Optional<Cinema> cinemaAux = cinemaRepository.findById((long)centralId);
        if (cinemaAux.isEmpty()){
            throw new EntityNotFoundException("No existe el cine");
        }

        Long newRoomNbr = addBroadcastAux(roomNbr, centralId);

        if (newRoomNbr == null){
            throw new EntityNotFoundException("No existe la sala");
        }

        Cinema cinema = cinemaAux.get();

        Room room = roomRepository.findRoomByCentralIdAndId((long)centralId, newRoomNbr);

        Optional<Film> filmAux = filmRepository.findById((long)filmCode);

        if (filmAux.isEmpty()){
            throw new EntityNotFoundException("No existe la pelicula");
        }

        if (filmAux == null){
            throw new EntityNotFoundException("No existe la pelicula");
        }

        Film film = filmAux.get();

        if(!(isScheduleAvailable(dateTime, film.getDuration(), centralId, roomNbr.intValue()))){
            throw new EntityNotFoundException("Ya hay una funcion a esa hora");
        }

        Broadcast newBroadcast = Broadcast.builder()
                .dateTime(dateTime)
                .broadcastPrice((long) broadcastPrice)
                .hasRoom(room)
                .centralId((long) centralId)
                .broadcastFilm(film)
                .build();

        return broadcastRepository.save(newBroadcast);
    }

    public boolean isScheduleAvailable(LocalDateTime newStartTime, Long durationMinutes, Integer centralId, Integer roomNbr)
    {
//        if (durationMinutes == null) {
//            throw new InvalidAttributeException("La duración no puede ser nula");
//        }
        LocalDateTime newEndTime = newStartTime.plusMinutes(durationMinutes);
        List<Broadcast> conflictingBroadcasts = broadcastRepository.findConflictingBroadcasts(
                (long)centralId, roomNbr, newStartTime, newEndTime, durationMinutes);
        return conflictingBroadcasts.isEmpty();
    }


    public List<Broadcast> getBroadcastsByFilmName(String filmName)
    {
        List <Broadcast> broadcasts = broadcastRepository.findByFilmNameBroadcast(filmName, LocalDateTime.now());
        return broadcasts;
    }

    public List<Broadcast> findByFilmAndCinema(String filmName, Long centralId) {
        return broadcastRepository.findByFilmCodeAndCentralId(filmName, centralId, LocalDateTime.now());
    }

    public List<Broadcast> findByFilmAndCinemaAndTime(Long filmCode, Long centralId, LocalDateTime dateTime) {
        return broadcastRepository.findByFilmCodeAndCentralIdAndDateTime(filmCode, centralId, dateTime);
    }

    public List<Long> getAssignedSeats(Long broadcastId)
    {
        return broadcastRepository.findAssignedSeatsByBroadcastId(broadcastId);
    }

}