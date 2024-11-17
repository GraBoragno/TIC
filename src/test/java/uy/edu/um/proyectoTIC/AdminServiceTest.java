package uy.edu.um.proyectoTIC;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.AdminRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.AdminService;
import uy.edu.um.proyectoTIC.services.ComboService;
import uy.edu.um.proyectoTIC.services.passwordService;

import java.time.LocalDate;
import java.util.*;

class AdminServiceTest {

    @Mock
    private SnackRepository snackRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ComboService comboService;

    @InjectMocks
    private AdminService adminService;

    @Mock
    private passwordService passwordService;

    private Snack snack1;
    private Snack snack2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        snack1 = new Snack();
        snack1.setSnackName("Papas");
        snack1.setSnackPrice("5");
        snack2 = new Snack();
        snack2.setSnackName("Agua");
        snack2.setSnackPrice("3");
    }

    @Test
    void testCreateComboInvalidAttributes()
    {
        Integer comboPrice = -10;
        List<String> snackNames = Arrays.asList("Papas", "Agua");
        String comboName = "Snack Combo";

        assertThrows(InvalidAttributeException.class, () -> {
            adminService.createCombo(comboPrice, snackNames, comboName);
        });
    }

    @Test
    void testCreateComboEmptyComboName()
    {
        Integer comboPrice = 10;
        List<String> snackNames = Arrays.asList("Papas", "Agua");
        String comboName = "";

        assertThrows(InvalidAttributeException.class, () -> {
            adminService.createCombo(comboPrice, snackNames, comboName);
        });
    }

    @Test
    void testCreateComboSnackNotFound()
    {
        Integer comboPrice = 10;
        List<String> snackNames = Arrays.asList("Papas", "Agua");
        String comboName = "Snack Combo";

        when(snackRepository.findById("Papas")).thenReturn(Optional.of(snack1));
        when(snackRepository.findById("Pop")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            adminService.createCombo(comboPrice, snackNames, comboName);
        });
    }

    @Test
    void testCreateComboSuccess() throws EntityNotFoundException
    {
        Integer comboPrice = 10;
        List<String> snackNames = Arrays.asList("Papas", "Agua");
        String comboName = "Snack Combo";

        when(snackRepository.findById("Papas")).thenReturn(Optional.of(snack1));
        when(snackRepository.findById("Agua")).thenReturn(Optional.of(snack2));

        Combo combo = new Combo();
        combo.setSnacksIncluded(Arrays.asList(snack1, snack2));
        combo.setComboPrice(comboPrice.longValue());
        combo.setComboName(comboName);

        when(comboService.addCombo(comboPrice, Arrays.asList(snack1, snack2), comboName)).thenReturn(combo);

        Combo createdCombo = adminService.createCombo(comboPrice, snackNames, comboName);

        assertNotNull(createdCombo);
        assertEquals(comboPrice.longValue(), createdCombo.getComboPrice());
        assertEquals(comboName, createdCombo.getComboName());
        assertEquals(2, createdCombo.getSnacksIncluded().size());
        assertTrue(createdCombo.getSnacksIncluded().contains(snack1));
        assertTrue(createdCombo.getSnacksIncluded().contains(snack2));
    }

    @Test
    void testCreateAdminSuccess() throws Exception
    {
        String email = "admin9@wtf.com";
        String name = "Admin";
        String address = "Calle";
        String birthdate = "1990-01-01";
        String password = "123456789";

        when(adminRepository.findById(email)).thenReturn(Optional.empty());

        when(passwordService.hashPassword(password)).thenReturn("hashedPassword");

        Admin admin = Admin.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(LocalDate.parse(birthdate))
                .password("hashedPassword")
                .build();

        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin createdAdmin = adminService.createAdmin(email, name, address, birthdate, password);

        assertNotNull(createdAdmin);
        assertEquals(email, createdAdmin.getEmail());
        assertEquals(name, createdAdmin.getName());
        assertEquals(address, createdAdmin.getAddress());
        assertEquals(LocalDate.parse(birthdate), createdAdmin.getBirthdate());
        assertEquals("hashedPassword", createdAdmin.getPassword());

        verify(adminRepository, times(1)).findById(email);
        verify(adminRepository, times(1)).save(any(Admin.class));
    }


    @Test
    void testCreateAdminInvalidEmail()
    {
        String email = "admin@gmail.com";
        String name = "Admin";
        String address = "Calle";
        String birthdate = "1990-01-01";
        String password = "123456789";

        InvalidIdException thrown = assertThrows(InvalidIdException.class, () -> {
            adminService.createAdmin(email, name, address, birthdate, password);
        });

        assertEquals("El email del administrador debe terminar en \"@wtf.com\"", thrown.getMessage());
    }

    @Test
    void testCreateAdminInvalidAttributes()
    {
        String email = "admin@wtf.com";
        String name = "";
        String address = "Calle";
        String birthdate = "1990-01-01";
        String password = "123456789";

        InvalidAttributeException thrown = assertThrows(InvalidAttributeException.class, () -> {
            adminService.createAdmin(email, name, address, birthdate, password);
        });

        assertEquals("Atributo no valido", thrown.getMessage());
    }

    @Test
    void testCreateAdminDuplicateEmail()
    {
        String email = "admin@wtf.com";
        String name = "Admin";
        String address = "Calle";
        String birthdate = "1990-01-01";
        String password = "123456789";

        when(adminRepository.findById(email)).thenReturn(Optional.of(new Admin()));

        DuplicateEntityException thrown = assertThrows(DuplicateEntityException.class, () -> {
            adminService.createAdmin(email, name, address, birthdate, password);
        });

        assertEquals("Ya existe un administrador con ese email", thrown.getMessage());
    }

    @Test
    void testCreateAdminInvalidPasswordLength()
    {
        String email = "admin@wtf.com";
        String name = "Admin";
        String address = "Calle";
        String birthdate = "1990-01-01";
        String password = "short";

        InvalidAttributeException thrown = assertThrows(InvalidAttributeException.class, () -> {
            adminService.createAdmin(email, name, address, birthdate, password);
        });

        assertEquals("Atributo no valido", thrown.getMessage());
    }
}


