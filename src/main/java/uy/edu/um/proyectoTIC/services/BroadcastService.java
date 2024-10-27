package uy.edu.um.proyectoTIC.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class BroadcastService {

    @Autowired
    private BroadcastRepository broadcastRepository;

    /*public Broadcast addBroadcast(String dateTimeS, Integer broadcastPrice, Integer roomNbr, Integer centralId, Integer filmCode)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeS, formatter);

        Broadcast newBroadcast = Broadcast.builder()
                .dateTime(dateTime)
                .broadcastPrice((long) broadcastPrice)
                .hasRoom((long) roomNbr)
                .centralId((long) centralId)
                .broadcastFilm((long) filmCode)
                .build();

        return broadcastRepository.save(newBroadcast);
    }*/

}