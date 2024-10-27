package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.entities.Room;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.RoomRepository;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    //agrega una room y tira EntityNotFoundException si no encuentra el cine
    public Room addRoom(Integer roomNbr, Integer centralId) throws EntityNotFoundException {

        Optional<Cinema> result = cinemaRepository.findById((long) centralId);

        if (result.isEmpty()){
            throw new EntityNotFoundException("El cine no existe");
        }

        Room newRoom = Room.builder()
                .roomNbr(roomNbr)
                .cinemaRoom(result.get())
                .build();

        return roomRepository.save(newRoom);
    }

}