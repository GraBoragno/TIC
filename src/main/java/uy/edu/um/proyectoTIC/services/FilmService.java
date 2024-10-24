package uy.edu.um.proyectoTIC.services;

import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.repository.FilmRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilmService {

    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // devuelve todas las pelis disponibles en todos los cines, en vdd ahora que lo pienso, no se fija si esta llena
    // pero basicamente no filta por ningun atributo. Es el de la pagin principal, solo tiene en cuenta la fecha actual
    public List<Film> getAvailableFilms() {
        LocalDateTime now = LocalDateTime.now();
        return filmRepository.findAvailableFilms(now);
    }
}
