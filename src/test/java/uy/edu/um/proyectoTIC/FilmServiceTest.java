package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.repository.FilmRepository;
import uy.edu.um.proyectoTIC.services.FilmService;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailableFilmsByDate()
    {

        when(filmRepository.findAvailableFilmsByDate(any())).thenReturn(List.of(new Film()));

        List<Film> films = filmService.getAvailableFilmsByDate();

        assertNotNull(films);
        assertFalse(films.isEmpty(), "Debería devolver una lista no vacía de películas disponibles");
    }

    @Test
    void testAddFilmSuccessfully() throws DuplicateEntityException, EntityNotFoundException
    {
        String filmName = "Dune";
        String directorName = "Denis Villeneuve";
        Integer duration = 155;
        String releaseYearDate = "2021";
        String genres = "Sci-Fi,Adventure";

        Film film = Film.builder()
                .filmName(filmName)
                .directorName(directorName)
                .duration((long) duration)
                .releaseYear(Year.parse(releaseYearDate))
                .genres(List.of("Sci-Fi", "Adventure"))
                .build();

        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.addFilm(filmName, directorName, duration, releaseYearDate, genres);

        assertNotNull(result);
        assertEquals(filmName, result.getFilmName());
        assertEquals(directorName, result.getDirectorName());
    }

    @Test
    void testAddFilmThrowsEntityNotFoundException()
    {
        String filmName = "Dune";
        String directorName = "Denis Villeneuve";
        Integer duration = 155;
        String releaseYearDate = "2021";
        String genres = "";

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            filmService.addFilm(filmName, directorName, duration, releaseYearDate, genres);
        });

        assertEquals("No hay genero", exception.getMessage());
    }

    @Test
    void testAddFilmThrowsDuplicateEntityException()
    {
        String filmName = "Dune";
        String directorName = "Denis Villeneuve";
        Integer duration = 155;
        String releaseYearDate = "2021";
        String genres = "Sci-Fi,Adventure";

        Film existingFilm = Film.builder()
                .filmName(filmName)
                .directorName(directorName)
                .duration((long) duration)
                .releaseYear(Year.parse(releaseYearDate))
                .genres(List.of("Sci-Fi", "Adventure"))
                .build();

        when(filmRepository.findByFilmName(filmName)).thenReturn(Optional.of(existingFilm));

        DuplicateEntityException exception = assertThrows(DuplicateEntityException.class, () -> {
            filmService.addFilm(filmName, directorName, duration, releaseYearDate, genres);
        });

        assertEquals("La película ya existe", exception.getMessage());

    }

    @Test
    void testParseGenres()
    {
        String genres = "Sci-Fi,Adventure,Action";

        List<String> result = filmService.parseGenres(genres);

        assertNotNull(result, "La lista no puede ser nula");
        assertEquals(3, result.size(), "Tiene que haber 3 géneros");
        assertEquals("sci-fi", result.get(0), "El primer género tiene que ser 'sci-fi'");
        assertEquals("adventure", result.get(1), "El segundo género tiene que ser 'adventure'");
        assertEquals("action", result.get(2), "El tercer género tiene que ser 'action'");
    }

    @Test
    void testFindByNameFilmExists()
    {
        String filmName = "Dune";
        Film film = Film.builder()
                .filmName(filmName)
                .directorName("Denis Villeneuve")
                .duration(155L)
                .releaseYear(Year.parse("2021"))
                .genres(List.of("Sci-Fi", "Adventure"))
                .build();

        when(filmRepository.findByFilmName(filmName)).thenReturn(Optional.of(film));

        Optional<Film> result = filmService.findByName(filmName);

        assertTrue(result.isPresent(), "La película debería existir");
        assertEquals(filmName, result.get().getFilmName(), "El nombre de la película tendria que ser igual");
    }

    @Test
    void testFindByNameFilmNotExists()
    {
        String filmName = "No existe";
        when(filmRepository.findByFilmName(filmName)).thenReturn(Optional.empty());

        Optional<Film> result = filmService.findByName(filmName);

        assertFalse(result.isPresent(), "La película no debería existir");
    }

    @Test
    void testGetAvailableFilms()
    {
        Film film1 = Film.builder()
                .filmName("Dune")
                .directorName("Denis Villeneuve")
                .duration(155L)
                .releaseYear(Year.parse("2021"))
                .genres(List.of("Sci-Fi", "Adventure"))
                .build();

        Film film2 = Film.builder()
                .filmName("Interstellar")
                .directorName("Christopher Nolan")
                .duration(169L)
                .releaseYear(Year.parse("2014"))
                .genres(List.of("Sci-Fi", "Drama"))
                .build();

        when(filmRepository.findAvailableFilms()).thenReturn(List.of(film1, film2));

        List<Film> result = filmService.getAvailableFilms();

        assertNotNull(result, "La lista de películas no debería ser nula");
        assertEquals(2, result.size(), "La lista debería tener 2 películas disponibles");
        assertTrue(result.contains(film1), "La lista debería tener la película 'Dune'");
        assertTrue(result.contains(film2), "La lista debería tener la película 'Interstellar'");
    }

    @Test
    void testGetAvailableFilmsEmpty()
    {
        when(filmRepository.findAvailableFilms()).thenReturn(List.of());

        List<Film> result = filmService.getAvailableFilms();

        assertNotNull(result, "La lista no debería ser nula");
        assertTrue(result.isEmpty(), "La lista debería estar vacía");
    }

    @Test
    void testRateFilm()
    {
        Integer filmCode = 1;
        float initialScore = 3.0f;
        int initialCounter = 3;

        Film film = Film.builder()
                .filmName("Dune")
                .directorName("Denis Villeneuve")
                .duration(155L)
                .releaseYear(Year.parse("2021"))
                .genres(List.of("Sci-Fi", "Adventure"))
                .score(initialScore)
                .scoreCounter(initialCounter)
                .build();

        when(filmRepository.findById((long) filmCode)).thenReturn(Optional.of(film));

        float newScore = 5.0f;

        filmService.rateFilm(filmCode, newScore);

        assertEquals(initialCounter + 1, film.getScoreCounter(), "El contador de calificaciones debería haberse incrementado");

        float expectedScore = ((initialScore * initialCounter) + newScore) / (initialCounter + 1);
        assertEquals(expectedScore, film.getScore(), 0.01, "El puntaje promedio debería haberse actualizado");

    }

    @Test
    void testRateFilmWithInvalidScore()
    {
        Integer filmCode = 1;
        float invalidLowScore = 0.5f;
        float invalidHighScore = 6.0f;

        Film film = Film.builder()
                .filmName("Dune")
                .directorName("Denis Villeneuve")
                .duration(155L)
                .releaseYear(Year.parse("2021"))
                .genres(List.of("Sci-Fi", "Adventure"))
                .score(8.0f)
                .scoreCounter(3)
                .build();

        when(filmRepository.findById((long) filmCode)).thenReturn(Optional.of(film));

        assertThrows(InvalidAttributeException.class, () -> filmService.rateFilm(filmCode, invalidLowScore),
                "Debería lanzar una InvalidAttributeException cuando el puntaje es menor que 1");

        assertThrows(InvalidAttributeException.class, () -> filmService.rateFilm(filmCode, invalidHighScore),
                "Debería lanzar una InvalidAttributeException cuando el puntaje es mayor que 5");

    }

    @Test
    void testFindTopRatedFilms()
    {
        Film film1 = Film.builder()
                .filmName("Dune")
                .directorName("Denis Villeneuve")
                .duration(155L)
                .releaseYear(Year.parse("2021"))
                .genres(List.of("Sci-Fi", "Adventure"))
                .score(4.5f)
                .scoreCounter(10)
                .build();

        Film film2 = Film.builder()
                .filmName("The Matrix")
                .directorName("The Wachowskis")
                .duration(136L)
                .releaseYear(Year.parse("1999"))
                .genres(List.of("Sci-Fi", "Action"))
                .score(5f)
                .scoreCounter(15)
                .build();

        Film film3 = Film.builder()
                .filmName("Inception")
                .directorName("Christopher Nolan")
                .duration(148L)
                .releaseYear(Year.parse("2010"))
                .genres(List.of("Sci-Fi", "Thriller"))
                .score(3.9f)
                .scoreCounter(20)
                .build();

        List<Film> films = Arrays.asList(film1, film2, film3);

        List<Film> sortedFilms = films.stream()
                .sorted((f1, f2) -> Float.compare(f2.getScore(), f1.getScore()))
                .collect(Collectors.toList());

        when(filmRepository.findTopRatedFilms()).thenReturn(sortedFilms);

        List<Film> topRatedFilms = filmRepository.findTopRatedFilms();

        assertNotNull(topRatedFilms);
        assertEquals(film2.getScore(), topRatedFilms.get(0).getScore());
        assertEquals(film1.getScore(), topRatedFilms.get(1).getScore());
        assertEquals(film3.getScore(), topRatedFilms.get(2).getScore());
    }
}

