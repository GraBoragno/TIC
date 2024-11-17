package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.exceptions.*;
import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoomSuccessfully() throws EntityNotFoundException, InvalidRoomQtyException
    {
        int roomNbr = 1;
        int centralId = 100;

        Cinema cinema = Cinema.builder()
                .centralId((long) centralId)
                .roomQty(5)
                .roomsCollection(new ArrayList<>())
                .build();

        Room newRoom = Room.builder()
                .roomNbr(roomNbr)
                .cinemaRoom(cinema)
                .build();

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));
        when(roomRepository.save(any(Room.class))).thenReturn(newRoom);

        Room result = roomService.createRoom(roomNbr, centralId);

        assertNotNull(result);
        assertEquals(roomNbr, result.getRoomNbr());
        assertEquals(cinema, result.getCinemaRoom());
    }

    @Test
    void testCreateRoomThrowsEntityNotFoundException()
    {
        int roomNbr = 1;
        int centralId = 100;

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            roomService.createRoom(roomNbr, centralId);
        });
    }

    @Test
    void testCreateRoomThrowsInvalidRoomQtyExceptionRoomAlreadyExists()
    {
        int roomNbr = 1;
        int centralId = 100;

        List<Room> existingRooms = new ArrayList<>();
        existingRooms.add(Room.builder().roomNbr(roomNbr).build());

        Cinema cinema = Cinema.builder()
                .centralId((long) centralId)
                .roomQty(5)
                .roomsCollection(existingRooms)
                .build();

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));

        assertThrows(InvalidRoomQtyException.class, () -> {
            roomService.createRoom(roomNbr, centralId);
        });
    }

    @Test
    void testCreateRoomThrowsInvalidRoomQtyExceptionRoomLimitReached()
    {
        int roomNbr = 1;
        int centralId = 100;

        List<Room> existingRooms = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            existingRooms.add(Room.builder().roomNbr(i).build());
        }

        Cinema cinema = Cinema.builder()
                .centralId((long) centralId)
                .roomQty(5)
                .roomsCollection(existingRooms)
                .build();

        when(cinemaRepository.findById((long) centralId)).thenReturn(Optional.of(cinema));

        assertThrows(InvalidRoomQtyException.class, () -> {
            roomService.createRoom(roomNbr, centralId);
        });
    }
}
