package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
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
    public List<Film> getAvailableFilms() {
        LocalDateTime now = LocalDateTime.now();
        return filmRepository.findAvailableFilms(now);
    }

    // los generos se pasan como un string, dsps se separan por coma y se meten a una lista
    // no estoy muy segura que chequee que la lista de generos no sea vacia
    // HAY QUE ARRGELAR LA PK
    //hay que ver si es nulo el film
    public Film addFilm(String filmName, String directorName, Integer duration, String releaseYearDate, String genres) throws EntityNotFoundException {
        Year releaseYear = Year.parse(releaseYearDate);

        Film film = findByName(filmName);

        if (film == null){
            throw new EntityNotFoundException("dgd");
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

    public Film findByName(String name) {
        Film film = filmRepository.findByFilmName(name);
        return film;
    }

}
