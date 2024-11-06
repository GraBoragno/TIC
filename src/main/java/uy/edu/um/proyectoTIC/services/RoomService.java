package uy.edu.um.proyectoTIC.services;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.entities.Room;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidRoomQtyException;
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
    @Transactional
    public Room createRoom(Integer roomNbr, Integer centralId) throws EntityNotFoundException
    {

        Optional<Cinema> result = cinemaRepository.findById((long) centralId);

        if (result.isEmpty()){
            throw new EntityNotFoundException("El cine no existe");
        }

        Cinema cinema = result.get();

        Room newRoom = Room.builder()
                .roomNbr(roomNbr)
                .cinemaRoom(cinema)
                .build();

        for (int i = 0; i < cinema.getRoomsCollection().size(); i++) {
            if (cinema.getRoomsCollection().get(i).getRoomNbr() == roomNbr){
                throw new InvalidRoomQtyException("El room no puede ser igual");
            }
        }

        //Ver porque se guarda dos veces
//        cinema.getRoomsCollection().add(newRoom);
//        cinemaRepository.save(cinema);

        return roomRepository.save(newRoom);
    }

    public Long getRoomIdByCentralIdAndRoomNbr(Long centralId, Integer roomNbr) {
        return roomRepository.findRoomIdByCentralIdAndRoomNbr(centralId, roomNbr);
    }

    public Room findRoomByCentralIdAndId(Long centralId, Long id) {
        return roomRepository.findRoomByCentralIdAndId(centralId, id);
    }

}