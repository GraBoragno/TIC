package uy.edu.um.proyectoTIC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uy.edu.um.proyectoTIC.entities.*;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;

import uy.edu.um.proyectoTIC.repository.*;
import uy.edu.um.proyectoTIC.services.*;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSeatSuccessfully() throws DuplicateEntityException
    {
        int row = 5;
        int column = 3;

        Seat newSeat = Seat.builder()
                .seatRow((long) row)
                .seatColumn((long) column)
                .build();

        when(seatRepository.findAll()).thenReturn(new ArrayList<>());
        when(seatRepository.save(any(Seat.class))).thenReturn(newSeat);

        Seat result = seatService.createSeatForTest(row, column);

        assertNotNull(result);
        assertEquals((long) row, result.getSeatRow());
        assertEquals((long) column, result.getSeatColumn());

    }

    @Test
    void testCreateSeatThrowsDuplicateEntityException()
    {
        int row = 5;
        int column = 3;

        Seat existingSeat = Seat.builder()
                .seatRow((long) row)
                .seatColumn((long) column)
                .build();

        when(seatRepository.findAll()).thenReturn(List.of(existingSeat));

        assertThrows(DuplicateEntityException.class, () -> seatService.createSeatForTest(row, column));
    }

    @Test
    void testFullSeats() throws DuplicateEntityException
    {
        List<Seat> seats = new ArrayList<>();

        when(seatRepository.save(any(Seat.class))).thenAnswer(invocation -> {
            Seat seat = invocation.getArgument(0);
            seats.add(seat);
            return seat;
        });

        seatService.fullSeats();

        assertEquals(150, seats.size());

        for (int i = 1; i <= 15; i++) {
            for (int j = 1; j <= 10; j++) {
                final int row = i;
                final int column = j;
                assertTrue(seats.stream().anyMatch(seat -> seat.getSeatRow() == row && seat.getSeatColumn() == column));
            }
        }

        verify(seatRepository, times(150)).save(any(Seat.class));
    }

}