package uy.edu.um.proyectoTIC;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import uy.edu.um.proyectoTIC.entities.Broadcast;
import uy.edu.um.proyectoTIC.entities.Cinema;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Room;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.BroadcastRepository;
import uy.edu.um.proyectoTIC.repository.CinemaRepository;
import uy.edu.um.proyectoTIC.repository.FilmRepository;
import uy.edu.um.proyectoTIC.repository.RoomRepository;
import uy.edu.um.proyectoTIC.services.BroadcastService;
import uy.edu.um.proyectoTIC.services.CinemaService;

import java.time.LocalDateTime;
import java.util.Optional;

class BroadcastServiceTest {

    @InjectMocks
    private BroadcastService broadcastService;

    @Mock
    private BroadcastRepository broadcastRepository;

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBroadcastCinemaNotFound()
    {
        Integer centralId = 1;
        String dateTimeS = "2024-11-18T14:00";
        Integer broadcastPrice = 100;
        Integer roomNbr = 2;
        Integer filmCode = 1;

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            broadcastService.addBroadcast(dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);
        });
    }

    @Test
    void testAddBroadcastRoomNotFound()
    {
        Integer centralId = 1;
        String dateTimeS = "2024-11-18T14:00";
        Integer broadcastPrice = 100;
        Integer roomNbr = 2;
        Integer filmCode = 1;

        Cinema cinema = Cinema.builder().centralId(1L).build();
        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));
        when(roomRepository.findRoomIdByCentralIdAndRoomNbr((long) centralId, roomNbr)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            broadcastService.addBroadcast(dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);
        });
    }

    @Test
    void testAddBroadcastFilmNotFound()
    {
        Integer centralId = 1;
        String dateTimeS = "2024-11-18T14:00";
        Integer broadcastPrice = 100;
        Integer roomNbr = 2;
        Integer filmCode = 1;

        Cinema cinema = Cinema.builder().centralId(1L).build();
        Room room = Room.builder().id(1).build();
        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));
        when(roomRepository.findRoomIdByCentralIdAndRoomNbr((long) centralId, roomNbr)).thenReturn(1L);
        when(roomRepository.findRoomByCentralIdAndId((long) centralId, 1L)).thenReturn(room);
        when(filmRepository.findById((long) filmCode)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            broadcastService.addBroadcast(dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);
        });
    }

    @Test
    void testAddBroadcastSuccessfully() throws EntityNotFoundException
    {
        Integer centralId = 1;
        String dateTimeS = "2024-11-18T14:00";
        Integer broadcastPrice = 100;
        Integer roomNbr = 2;
        Integer filmCode = 1;

        Cinema cinema = Cinema.builder().centralId(1L).build();
        Room room = Room.builder().roomNbr(2).build();
        Film film = Film.builder()
                .filmCode(1L)
                .duration(120L)
                .build();

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));
        when(roomRepository.findRoomByCentralIdAndId((long) centralId, 2L)).thenReturn(room);
        when(filmRepository.findById((long) filmCode)).thenReturn(Optional.of(film));
        when(broadcastRepository.save(any(Broadcast.class))).thenReturn(new Broadcast());


        Broadcast result = broadcastService.addBroadcast(dateTimeS, broadcastPrice, roomNbr, centralId, filmCode);

        assertNotNull(result);

        verify(broadcastRepository, times(1)).save(any(Broadcast.class));
    }

}

