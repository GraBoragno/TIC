package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.users.*;
import uy.edu.um.proyectoTIC.entities.Ticket;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidCardNbr;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.repository.TicketRepository;
import uy.edu.um.proyectoTIC.services.ClientService;
import uy.edu.um.proyectoTIC.services.passwordService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepo;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private passwordService passwordService;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClientSuccessfully()
    {
        String email = "test@email.com";
        String name = "Nombre";
        String address = "Calle";
        String date = "2000-01-01";
        String password = "password123";
        String hashedPassword = "hashedPassword";

        Client newClient = Client.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(LocalDate.parse(date))
                .password(hashedPassword)
                .build();

        when(passwordService.hashPassword(password)).thenReturn(hashedPassword);
        when(clientRepo.save(any(Client.class))).thenReturn(newClient);

        Client result = clientService.addClient(email, name, address, date, password);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(name, result.getName());
        assertEquals(address, result.getAddress());
        assertEquals(LocalDate.parse(date), result.getBirthdate());
        assertEquals(hashedPassword, result.getPassword());
    }

    @Test
    void testAddClientInvalidPassword()
    {
        String email = "test@email.com";
        String name = "Nombre";
        String address = "Calle";
        String date = "2000-01-01";
        String password = "short";

        InvalidAttributeException exception = assertThrows(
                InvalidAttributeException.class,
                () -> clientService.addClient(email, name, address, date, password)
        );

        assertEquals("Atributo no valido", exception.getMessage());
    }

    @Test
    void testCancelTicketSuccessfully()
    {
        Client client = Client.builder()
                .email("test@email.com")
                .build();

        client.setTicketsBought(new ArrayList<>());

        Ticket ticket = Ticket.builder()
                .clientTicket(client)
                .build();

        client.getTicketsBought().add(ticket);

        doNothing().when(ticketRepository).delete(ticket);

        clientService.cancelTicket(ticket);

        assertTrue(client.getTicketsBought().isEmpty());

        verify(ticketRepository, times(1)).delete(ticket);
    }


    @Test
    void testFindByEmailSuccessfully() throws EntityNotFoundException
    {
        String email = "test@email.com";
        Client client = Client.builder().email(email).build();

        when(clientRepo.findById(email)).thenReturn(Optional.of(client));

        Client result = clientService.findByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindByEmailNotFound()
    {
        String email = "test@email.com";

        when(clientRepo.findById(email)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> clientService.findByEmail(email)
        );

        assertEquals("No existe el cliente", exception.getMessage());
    }

    @Test
    void testUpdateNameSuccessfully() throws EntityNotFoundException
    {
        String email = "test@email.com";
        String newName = "Nombre";
        Client client = Client.builder().email(email).name("Nombre").build();

        when(clientRepo.findById(email)).thenReturn(Optional.of(client));
        when(clientRepo.save(any(Client.class))).thenReturn(client);

        Client result = clientService.UpdateName(email, newName);

        assertNotNull(result);
        assertEquals(newName, result.getName());
    }

    @Test
    void testUpdateNameInvalidName()
    {
        String email = "test@email.com";

        InvalidIdException exception = assertThrows(
                InvalidIdException.class,
                () -> clientService.UpdateName(email, null)
        );

        assertEquals("El nombre no puede ser vacio", exception.getMessage());
    }

    @Test
    void testUpdatePaymentMethodSuccessfully() throws EntityNotFoundException
    {
        String email = "test@email.com";
        Long cardNbr = 1234567812345670L;
        Client client = Client.builder().email(email).build();

        when(clientRepo.findById(email)).thenReturn(Optional.of(client));
        when(clientRepo.save(any(Client.class))).thenReturn(client);

        boolean result = clientService.UpdatePaymentMethod(email, cardNbr);

        assertTrue(result);
        verify(clientRepo, times(1)).save(client);
    }

    @Test
    void testUpdatePaymentMethodInvalidCardNumber()
    {
        String email = "test@email.com";
        Long invalidCardNbr = 123L;

        InvalidCardNbr exception = assertThrows(
                InvalidCardNbr.class,
                () -> clientService.UpdatePaymentMethod(email, invalidCardNbr)
        );

        assertEquals("El número de tarjeta no es valido", exception.getMessage());
    }
}

