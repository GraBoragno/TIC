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
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Mock
    private SnackRepository snackRepository;

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
    void testBuyTicketSuccessfully() throws DuplicateEntityException, EntityNotFoundException {
        int broadcastId = 1;
        int row = 3;
        int column = 5;
        String email = "test@client.com";

        List<Combo> combos = List.of(comboService.addCombo(100, List.of(snackService.addSnack("Popcorn", "20"), snackService.addSnack("Soda", "15")) , "Combo1"));
        List<Snack> snacks = List.of(snackService.addSnack("Popcorn", "20"), snackService.addSnack("Soda", "15"));

        Seat selectedSeat = seatService.createSeatForTest(row, column);

        Broadcast broadcast = new Broadcast();
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

        when(broadcastRepository.findById((long) broadcastId)).thenReturn(Optional.of(broadcast));
        when(clientRepository.findById(email)).thenReturn(Optional.of(client));
        when(seatRepository.findAll()).thenReturn(List.of(seatService.createSeatForTest(1, 1), selectedSeat));
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


}