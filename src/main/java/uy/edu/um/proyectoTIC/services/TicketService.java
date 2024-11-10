package uy.edu.um.proyectoTIC.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.repository.SeatRepository;
import uy.edu.um.proyectoTIC.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BroadcastRepository broadcastRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ClientRepository clientRepository;

    //Las listas de combo y Snack se pasan siempre, incluso vacias
    //Agregarlo a la lista del ticket del cliente
    @Transactional
    public Ticket buyTicket(int broadcastId, int row, int column, String email, List<Combo> combos, List<Snack> snacks)
    {
        Optional<Broadcast> broadcastAux = broadcastRepository.findById((long) broadcastId);
        Broadcast broadcast = broadcastAux.get();

        Optional<Client> clientAux = clientRepository.findById(email);
        Client client = clientAux.get();


        long ticketPrice = calculatePrice(combos,snacks, broadcast.getBroadcastPrice());

        List<Seat> seats = seatRepository.findAll();
        Seat selectedSeat = null;

        for (int i = 0; i < seats.size(); i++) {
            if ((long)row == seats.get(i).getSeatRow() && (long)column == seats.get(i).getSeatColumn()){
                selectedSeat = seats.get(i);
            }
        }

        broadcast.getAssignedSeatsId().add(selectedSeat.getSeatId());

        Ticket ticket = Ticket.builder()
                .clientTicket(client)
                .ticketBroadcast(broadcast)
                .assignedSeat(selectedSeat)
                .ticketPrice(ticketPrice)
                .admittedCombos(combos)
                .snacks(snacks)
                .build();

        return ticketRepository.save(ticket);
    }

    private long calculatePrice(List<Combo> combos, List<Snack> snacks, Long broadcastPrice)
    {
        long price = broadcastPrice;

        for (int i = 0; i < combos.size(); i++) {
            price += combos.get(i).getComboPrice();
        }

        for (int i = 0; i < snacks.size(); i++) {
            price += Long.parseLong(snacks.get(i).getSnackPrice());
        }
        return price;
    }


}
