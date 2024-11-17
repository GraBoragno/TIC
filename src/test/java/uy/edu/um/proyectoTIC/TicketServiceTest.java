package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private BroadcastRepository broadcastRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private TicketService ticketService;

    @InjectMocks
    private ComboService comboService;

    @InjectMocks
    private SnackService snackService;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuyTicketSuccessfully() throws DuplicateEntityException
    {
        int broadcastId = 1;
        int row = 3;
        int column = 5;
        String email = "test@client.com";

        Snack popcorn = new Snack();
        popcorn.setSnackName("pop");
        popcorn.setSnackPrice("20");

        Snack soda = new Snack();
        soda.setSnackName("Coca");
        soda.setSnackPrice("15");

        List<Snack> snacks = List.of(popcorn, soda);

        Combo combo = new Combo();
        combo.setComboName("Combo1");
        combo.setComboPrice(100L);
        combo.setSnacksIncluded(snacks);

        List<Combo> combos = List.of(combo);

        Seat selectedSeat = new Seat();
        selectedSeat.setSeatRow(3L);
        selectedSeat.setSeatColumn(5L);

        when(seatService.createSeat(row, column)).thenReturn(selectedSeat);

        Broadcast broadcast = new Broadcast();
        broadcast.setBroadcastPrice(50L);
        Client client = new Client();

        long expectedPrice = 100 + 50 + 20 + 15;

        Ticket expectedTicket = Ticket.builder()
                .clientTicket(client)
                .ticketBroadcast(broadcast)
                .assignedSeat(selectedSeat)
                .ticketPrice(expectedPrice)
                .admittedCombos(combos)
                .snacks(snacks)
                .build();

        List<Seat> seats = List.of(selectedSeat);

        when(broadcastRepository.findById((long) broadcastId)).thenReturn(Optional.of(broadcast));
        when(clientRepository.findById(email)).thenReturn(Optional.of(client));
        when(seatRepository.findAll()).thenReturn(seats);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        Ticket result = ticketService.buyTicket(broadcastId, row, column, email, combos, snacks);

        assertNotNull(result);
        assertEquals(expectedPrice, result.getTicketPrice());
        assertEquals(client, result.getClientTicket());
        assertEquals(broadcast, result.getTicketBroadcast());
        assertEquals(selectedSeat, result.getAssignedSeat());
        assertEquals(combos, result.getAdmittedCombos());
        assertEquals(snacks, result.getSnacks());
    }

    @Test
    void testCalculatePrice()
    {
        Combo combo1 = new Combo();
        combo1.setComboPrice(100L);
        Combo combo2 = new Combo();
        combo2.setComboPrice(150L);

        Snack snack1 = new Snack();
        snack1.setSnackPrice("20");
        Snack snack2 = new Snack();
        snack2.setSnackPrice("15");

        List<Combo> combos = List.of(combo1, combo2);
        List<Snack> snacks = List.of(snack1, snack2);

        Long broadcastPrice = 50L;

        long expectedPrice = 50 + 100 + 150 + 20 + 15;

        long actualPrice = ticketService.calculatePrice(combos, snacks, broadcastPrice);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void testDeleteTicket() throws EntityNotFoundException {
        Long ticketId = 1L;

        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketId);

        Client client = new Client();
        client.setTicketsBought(new ArrayList<>());
        ticket.setClientTicket(client);
        client.getTicketsBought().add(ticket);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        ticketService.deleteTicket(ticketId);

        assertFalse(client.getTicketsBought().contains(ticket));
        verify(clientRepository).save(client);
        verify(ticketRepository).delete(ticket);
    }


}