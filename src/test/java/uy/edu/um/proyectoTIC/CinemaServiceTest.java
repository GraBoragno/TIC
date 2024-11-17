package uy.edu.um.proyectoTIC;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.exceptions.InvalidRoomQtyException;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.services.CinemaService;

import java.util.Optional;

class CinemaServiceTest {

    @InjectMocks
    private CinemaService cinemaService;

    @Mock
    private CinemaRepository cinemaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCinemaWithInvalidId()
    {
        assertThrows(InvalidIdException.class, () -> {
            cinemaService.addCinema(null, 5, "Centro");
        });
    }

    @Test
    void testAddCinemaWithExistingCinema()
    {
        Integer centralId = 1;
        Integer roomQty = 5;
        String neighborhood = "Centro";

        Cinema existingCinema = Cinema.builder().centralId(1L).roomQty(5).neighborhood("Centro").build();

        when(cinemaRepository.findById(1L)).thenReturn(Optional.of(existingCinema));

        assertThrows(DuplicateEntityException.class, () -> {
            cinemaService.addCinema(centralId, roomQty, neighborhood);
        });
    }

    @Test
    void testAddCinemaWithExceedingRoomQty()
    {
        Integer centralId = 1;
        Integer roomQty = 11;
        String neighborhood = "Centro";

        assertThrows(InvalidRoomQtyException.class, () -> {
            cinemaService.addCinema(centralId, roomQty, neighborhood);
        });
    }

    @Test
    void testAddCinemaSuccessfully() throws DuplicateEntityException, InvalidIdException, InvalidRoomQtyException
    {
        Integer centralId = 1;
        Integer roomQty = 5;
        String neighborhood = "Centro";

        Cinema newCinema = Cinema.builder().centralId(1L).roomQty(5).neighborhood("Centro").build();

        when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());
        when(cinemaRepository.save(any(Cinema.class))).thenReturn(newCinema);

        Cinema result = cinemaService.addCinema(centralId, roomQty, neighborhood);

        assertNotNull(result);
        assertEquals(1L, result.getCentralId());
        assertEquals(5, result.getRoomQty());
        assertEquals("Centro", result.getNeighborhood());

        verify(cinemaRepository, times(1)).save(any(Cinema.class));
    }
}

