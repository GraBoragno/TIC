package uy.edu.um.proyectoTIC.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.Ticket;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidAttributeException;
import uy.edu.um.proyectoTIC.exceptions.InvalidCardNbr;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.repository.TicketRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private passwordService passwordService;


    public Client addClient(String email, String name, String address, String date,String password)
    {
        if (password.length() < 8){
            throw new InvalidAttributeException("Atributo no valido");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(date, formatter);

        String hashedPassword = passwordService.hashPassword(password);

        Client newClient = Client.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(birthDate)
                .password(hashedPassword)
                .build();
        return clientRepo.save(newClient);
    }

    @Transactional
    public void cancelTicket(Ticket ticket)
    {
        Client client = ticket.getClientTicket();
        if (client != null)
            client.getTicketsBought().remove(ticket);

        ticketRepository.delete(ticket);
    }

    public Client findByEmail(String email) throws EntityNotFoundException
    {
        Optional<Client> client = clientRepo.findById(email);
        if(client.isEmpty())
            throw new EntityNotFoundException("No existe el cliente");
        Client clientTemp = client.get();

        return clientTemp;
    }

    public Client UpdateName(String email,String name) throws EntityNotFoundException
    {
        if (name == null)
                throw new InvalidIdException("El nombre no puede ser vacio");

         Client client = this.findByEmail(email);
         client.setName(name);
         clientRepo.save(client);
         return clientRepo.save(client);
    }

    public Client UpdateAddress(String email,String address) throws EntityNotFoundException
    {
        if (address == null)
            throw new InvalidIdException("El nombre no puede ser vacio");

        Client client = this.findByEmail(email);
        client.setAddress(address);
        return clientRepo.save(client);
    }

    public Client UpdateDate(String email,String date) throws EntityNotFoundException
    {
        if (date == null)
            throw new InvalidIdException("Fecha no valida");

        Client client = this.findByEmail(email);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(date, formatter);
        client.setBirthdate(birthDate);
        return clientRepo.save(client);
    }

    public Client UpdatePassword(String email, String password) throws EntityNotFoundException
    {
        if (password == null || password.length() < 8)
            throw new InvalidIdException("Contraseña no apta");

        Client client = this.findByEmail(email);
        client.setPassword(password);
        return clientRepo.save(client);
    }

    public Boolean UpdatePaymentMethod(String email, Long cardNbr) throws EntityNotFoundException
    {
        if (email == null || email.isEmpty() ){
            throw new InvalidIdException("El email no cumple con el formato adecuado");
        }

        if (!cardNbrValidator(cardNbr)){
            throw new InvalidCardNbr("El número de tarjeta no es valido");
        }

        Optional<Client> result = clientRepo.findById(email);
        boolean success = false;

        if(result.isEmpty()){
            throw new EntityNotFoundException("El usuario no existe");
        }
        else{
            Client wantedClient = result.get();
            wantedClient.setCardNbr(cardNbr);
            clientRepo.save(wantedClient);
            success = true;
        }
        return success;
    }

    //Usa el algoritmo de Luhn
    private boolean cardNbrValidator(Long cardNbr)
    {
        String cardNumber = cardNbr.toString();
        int NbrLength = cardNumber.length();
        int sum = 0;
        boolean isSecond = false;

        for (int i = NbrLength - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond) {
                d = d * 2;
                if (d > 9) {
                    d = d - 9;
                }
            }
            sum += d;
            isSecond = (!isSecond);
        }
        return (sum % 10 == 0);
    }


    @Transactional
    public List<Ticket> getTicketByEmail(String email){

        return(clientRepo.findTicketsByEmail(email));

    }

}
