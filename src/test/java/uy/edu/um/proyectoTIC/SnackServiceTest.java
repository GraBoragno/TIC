package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.util.Optional;

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
}
