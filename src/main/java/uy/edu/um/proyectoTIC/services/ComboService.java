package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidCardNbr;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.repository.ComboRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private SnackRepository snackRepository;

    // (-_-) -> (o_-) -> (0_-) -> (0_o) -> (0_0) -> (T-T)
    public Combo addCombo(Integer comboPrice, List<Snack> snacks, String comboName) throws EntityNotFoundException
    {

        Combo newCombo = Combo.builder()
                .comboPrice((long)comboPrice)
                .snacksIncluded(snacks)
                .comboName(comboName)
                .build();

        return comboRepository.save(newCombo);
    }

    public List<Combo> getAvailableCombos() {
        return comboRepository.findAvailableCombos();
    }
}