package uy.edu.um.proyectoTIC.services;

import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.repository.FilmRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // devuelve todas las pelis disponibles en todos los cines solo filtra por fecha actual en adelante
    public List<Film> getAvailableFilms() {
        LocalDateTime now = LocalDateTime.now();
        return filmRepository.findAvailableFilms(now);
    }

    // los generos se pasan como un string, dsps se separan por coma y se meten a una lista
    //no estoy muy segura que chequee que la lista de generos no sea vacia
    //HAY QUE ARRGELAR LA PK
    public Film addFilm(String filmName, String directorName, Integer duration, String releaseYearDate, String genres)
    {
        Year releaseYear = Year.parse(releaseYearDate);

        Long filmCode = generateFilmCode();

        List<String> genreList = parseGenres(genres);

        Film newFilm = Film.builder()
                .filmCode(filmCode)
                .filmName(filmName)
                .directorName(directorName)
                .duration((long) duration)
                .releaseYear(releaseYear)
                .genres(genreList)
                .build();

        return filmRepository.save(newFilm);
    }

    // genera el codigo de las pelis aleatorios hasta que no coincida con ningun otro
    public Long generateFilmCode()
    {
        Random random = new Random();
        Long filmCode = Math.abs(random.nextLong());

        while (filmRepository.existsByFilmCode(filmCode)) {
            filmCode = Math.abs(random.nextLong());
        }
        return filmCode;
    }

    // parsea el string de generos en una lista
    //por alguna razon si pones tipo "cienciA fi ccion" en la bd lo guarda como "ciencia fi ccion" y no le saca los espacios
    public List<String> parseGenres(String genres)
    {
        return Arrays.stream(genres.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

}
