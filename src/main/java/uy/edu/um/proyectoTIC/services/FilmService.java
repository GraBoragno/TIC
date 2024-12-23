package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.repository.FilmRepository;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    

    // devuelve todas las pelis disponibles en todos los cines solo filtra por fecha actual en adelante
    public List<Film> getAvailableFilmsByDate() {
        LocalDateTime now = LocalDateTime.now();
        return filmRepository.findAvailableFilmsByDate(now);
    }

    // los generos se pasan como un string, dsps se separan por coma y se meten a una lista
    public Film addFilm(String filmName, String directorName, Integer duration, String releaseYearDate, String genres) throws DuplicateEntityException, EntityNotFoundException
    {
        Year releaseYear = Year.parse(releaseYearDate);

        if (genres.length() == 0)
            throw new EntityNotFoundException("No hay genero");

        Optional<Film> existingFilm = filmRepository.findByFilmName(filmName);

        if (existingFilm.isPresent()) {
            throw new DuplicateEntityException("La película ya existe");
        }

        List<String> genreList = parseGenres(genres);

        Film newFilm = Film.builder()
                .filmName(filmName)
                .directorName(directorName)
                .duration((long) duration)
                .releaseYear(releaseYear)
                .genres(genreList)
                .build();

        return filmRepository.save(newFilm);
    }

    // parsea el string de generos en una lista
    public List<String> parseGenres(String genres) {
        return Arrays.stream(genres.split(","))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public Optional<Film> findByName(String name) {
        return filmRepository.findByFilmName(name);
    }

    public List<Film> getAvailableFilms() {
        return filmRepository.findAvailableFilms();
    }

    public void rateFilm(Integer filmCode, float score) throws InvalidAttributeException
    {
        if (score < 1 || score > 5) {
            throw new InvalidAttributeException("El puntaje no es valido");
        }

        Optional<Film> filmAux = filmRepository.findById((long)filmCode);
        Film film = filmAux.get();
        int prevCounter = film.getScoreCounter();

        film.setScoreCounter(prevCounter+1);
        film.setScore(((film.getScore()*prevCounter)+score)/film.getScoreCounter());
        filmRepository.save(film);
    }

    public List<Film> getRatedFilms() {
        return filmRepository.findTopRatedFilms();
    }

}
