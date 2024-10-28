package uy.edu.um.proyectoTIC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.exceptions.InvalidCardNbr;
import uy.edu.um.proyectoTIC.exceptions.InvalidIdException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepo;


    public Client addClient(String email, String name, String address, String date,String password)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(date, formatter);

        Client newClient = Client.builder()
                .email(email)
                .name(name)
                .address(address)
                .birthdate(birthDate)
                .password(password)   //Se Prueba a verificar el largo con HTML
                .build();
        return clientRepo.save(newClient);
    }


    public Client findByEmail(String email) throws EntityNotFoundException
    {
        Optional<Client> client = clientRepo.findById(email);
        if(client.isEmpty())
            throw new EntityNotFoundException("No existe el cliente");
        Client clientTemp = client.get();

        return clientTemp;
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
        Boolean success = false;

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

}
