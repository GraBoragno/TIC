package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Seat;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.repository.SeatRepository;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    private Seat createSeat(int row, int column) throws DuplicateEntityException
    {
        List<Seat> allSeats = seatRepository.findAll();
        for (int i = 0; i < allSeats.size(); i++) {
            Seat seatAux = allSeats.get(i);
            if (seatAux.getSeatRow() == (long)row && seatAux.getSeatColumn() == (long)column){
                throw new DuplicateEntityException("El asiento ya existe");
            }
        }
        Seat newSeat = Seat.builder()
                .seatRow((long)row)
                .seatColumn((long)column)
                .build();
        
        return seatRepository.save(newSeat);
    }
    
    public void fullSeats() throws DuplicateEntityException {
        for (int i = 1; i <= 15 ; i++) {
            for (int j = 1; j <= 10 ; j++) {
                this.createSeat(i,j);
            }
        }
    }
}
