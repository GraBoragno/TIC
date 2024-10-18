package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.repository.ClientRepository;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;

    //AddCard

    public Client addClient(String email, String name, String address, String date)
    {
        LocalDate birthDate = LocalDate.parse(date);

        Client newClient = Client.builder()
                .email()
                .build();
    }
}
