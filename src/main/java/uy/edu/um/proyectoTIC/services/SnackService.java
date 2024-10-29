package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.SnackRepository;

import java.util.Optional;


@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepository;

    // agrega el snack, tira InvalidIdException si es nula y tira DuplicateEntityException si ya existe el snack
    public Snack addSnack(String snackName, String snackPrice) throws DuplicateEntityException
    {
        if (snackName == null || snackName.isEmpty()){
            throw new InvalidIdException("La Id es invalida");
        }

        Optional<Snack> result = snackRepository.findById(snackName);

        if(!result.isEmpty()){
            throw new DuplicateEntityException("El snack ya existe");
        }

        Snack newSnack = Snack.builder()
                .snackName(snackName)
                .snackPrice(snackPrice)
                .build();
        return snackRepository.save(newSnack);
    }

}