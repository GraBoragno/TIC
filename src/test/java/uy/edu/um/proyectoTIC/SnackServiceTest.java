package uy.edu.um.proyectoTIC;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SnackServiceTest {

    @Mock
    private SnackRepository snackRepository;

    @InjectMocks
    private SnackService snackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSnackSuccessfully() throws DuplicateEntityException
    {
        String snackName = "Popcorn";
        String snackPrice = "20";

        Snack snack = Snack.builder()
                .snackName(snackName)
                .snackPrice(snackPrice)
                .build();

        when(snackRepository.findById(snackName)).thenReturn(Optional.empty());
        when(snackRepository.save(any(Snack.class))).thenReturn(snack);

        Snack result = snackService.addSnack(snackName, snackPrice);

        assertNotNull(result);
        assertEquals(snackName, result.getSnackName());
        assertEquals(snackPrice, result.getSnackPrice());
    }

    @Test
    void testAddSnackThrowsDuplicateEntityException()
    {
        String snackName = "Popcorn";
        String snackPrice = "20";

        Snack existingSnack = Snack.builder()
                .snackName(snackName)
                .snackPrice(snackPrice)
                .build();

        when(snackRepository.findById(snackName)).thenReturn(Optional.of(existingSnack));

        assertThrows(DuplicateEntityException.class, () -> snackService.addSnack(snackName, snackPrice));
    }

    @Test
    void testAddSnackThrowsInvalidIdException()
    {
        String invalidSnackName = null;
        String snackPrice = "20";

        assertThrows(InvalidIdException.class, () -> snackService.addSnack(invalidSnackName, snackPrice));
    }

    @Test
    void testFindAvailableSnacks() {
        Snack snack1 = Snack.builder().snackName("Popcorn").snackPrice("20").build();
        Snack snack2 = Snack.builder().snackName("Agua").snackPrice("15").build();

        when(snackRepository.findAvailableSnacks()).thenReturn(List.of(snack1, snack2));

        List<Snack> availableSnacks = snackService.getAvailableSnacks();

        assertEquals(2, availableSnacks.size());
        assertTrue(availableSnacks.contains(snack1));
        assertTrue(availableSnacks.contains(snack2));
    }

}
