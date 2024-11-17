package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.ComboRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.ComboService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComboServiceTest {

    @Mock
    private ComboRepository comboRepository;

    @Mock
    private SnackRepository snackRepository;

    @InjectMocks
    private ComboService comboService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComboSuccessfully() throws EntityNotFoundException
    {
        int comboPrice = 1000;
        String comboName = "Combo Prueba";
        List<Snack> snacks = new ArrayList<>();
        snacks.add(Snack.builder().snackName("Pop").snackPrice("500").build());
        snacks.add(Snack.builder().snackName("Agua").snackPrice("300").build());

        Combo newCombo = Combo.builder()
                .comboPrice((long) comboPrice)
                .snacksIncluded(snacks)
                .comboName(comboName)
                .build();

        when(comboRepository.save(any(Combo.class))).thenReturn(newCombo);

        Combo result = comboService.addCombo(comboPrice, snacks, comboName);

        assertNotNull(result);
        assertEquals(comboPrice, result.getComboPrice());
        assertEquals(comboName, result.getComboName());
        assertEquals(snacks.size(), result.getSnacksIncluded().size());
        assertEquals(snacks, result.getSnacksIncluded());
    }

    @Test
    void testAddComboWithEmptySnackList() throws EntityNotFoundException
    {
        int comboPrice = 800;
        String comboName = "Combo Prueba";
        List<Snack> snacks = new ArrayList<>();

        Combo newCombo = Combo.builder()
                .comboPrice((long) comboPrice)
                .snacksIncluded(snacks)
                .comboName(comboName)
                .build();

        when(comboRepository.save(any(Combo.class))).thenReturn(newCombo);

        Combo result = comboService.addCombo(comboPrice, snacks, comboName);

        assertNotNull(result);
        assertEquals(comboPrice, result.getComboPrice());
        assertEquals(comboName, result.getComboName());
        assertEquals(0, result.getSnacksIncluded().size());
    }
}
