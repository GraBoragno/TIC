package uy.edu.um.proyectoTIC.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.exceptions.InvalidRoomQtyException;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.SnackRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SnackRepository snackRepository;

    // agrega un cine nuevo, si el id existe tira DuplicateEntityException, si el id es vacio InvalidException
    // y si la cantidad de room es mayor a 10 tira InvalidRoomQtyException
    public Cinema addCinema(Integer centralId, Integer roomQty, String neighborhood) throws DuplicateEntityException
    {
        if (centralId == null){
            throw new InvalidIdException("La Id es invalida");
        }

        Optional<Cinema> result = cinemaRepository.findById((long)centralId);

        if(!result.isEmpty()){
            throw new DuplicateEntityException("El cine ya existe");
        }

        if(roomQty > 10){
            throw new InvalidRoomQtyException("Excede el numero maximo de salas");
        }

        Cinema newCinema = Cinema.builder()
                .centralId((long) centralId)
                .roomQty(roomQty)
                .neighborhood(neighborhood)
                .build();
        return cinemaRepository.save(newCinema);
    }

    //No le entra ningun argumento porque todos los cines tienen las mismas Snacks
    public List<Snack> getSnacks() throws InvalidIdException
    {
        return snackRepository.findAll();
    }

    public List<Cinema> findCinemasByCentralIds(List<Long> centralIds) {
        return cinemaRepository.findByCentralIdIn(centralIds);
    }

}
