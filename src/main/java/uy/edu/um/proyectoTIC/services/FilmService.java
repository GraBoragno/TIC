package uy.edu.um.proyectoTIC.services;

import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.repository.FilmRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAvailableFilms() {
        LocalDateTime now = LocalDateTime.now();
        return filmRepository.findAvailableFilms(now);
    }
}
