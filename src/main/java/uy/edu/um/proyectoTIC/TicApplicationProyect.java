package uy.edu.um.proyectoTIC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.time.LocalDate;

@SpringBootApplication
@Component
@EnableJpaRepositories(basePackages = "uy.edu.um.proyectoTIC.repository")
@EntityScan(basePackages = "uy.edu.um.proyectoTIC.entities")
public class TicApplicationProyect {

	@Autowired
	private ClientService clientService;

	@Autowired
	private FilmService filmService;

	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private SnackService snackService;

	public static void main(String[] args) throws DuplicateEntityException {
		ApplicationContext ctx = SpringApplication.run(TicApplicationProyect.class, args);
		TicApplicationProyect app = ctx.getBean(TicApplicationProyect.class);
		app.runInCommandLine();
	}

	public void runInCommandLine() throws DuplicateEntityException {
		Client newClient = clientService.addClient("email@email.com","persona1","xd", "21/10/2024","1234");

//		filmService.addFilm("Dune", "Denis Villeneuve", 155, "2021", "ciencia ficción, aventura");
//		filmService.addFilm("Dune: Part Two", "Denis Villeneuve", 160, "2023", "ciencia ficción, aventura");
//		filmService.addFilm("Inception", "Christopher Nolan", 148, "2010", "cie ncia ficción, thriller");
//		filmService.addFilm("The Matrix", "Wachowski Sisters", 136, "1999", "acción, ciencia ficción");
//		filmService.addFilm("Interstellar", "Christopher Nolan", 169, "2014", "ciencia ficción, drama");

		try {
			cinemaService.addCinema(1, 8, "Punta Carretas");
			cinemaService.addCinema(2, 5, "Ciudad Vieja");
			cinemaService.addCinema(3, 7, "Pocitos");
			cinemaService.addCinema(4, 4, "Carrasco");
			cinemaService.addCinema(5, 6, "Tres Cruces");
			cinemaService.addCinema(6, 10, "Centro");
			cinemaService.addCinema(7, 3, "Malvín");
			cinemaService.addCinema(8, 6, "Buceo");
		} catch (DuplicateEntityException e) {
			System.out.println("Error: Ya existe un cine con los mismos datos.");
		}

		try {
			roomService.addRoom(1, 1);
			roomService.addRoom(1, 10);
		} catch (EntityNotFoundException e) {
			System.out.println("Error: El cine no existe");
        }

		try {
			snackService.addSnack("Pop dulce", "200");
			snackService.addSnack("Nachos", "250");
			snackService.addSnack("Pop salado", "150");
			snackService.addSnack("Nachos", "300");
		} catch (DuplicateEntityException e) {
			System.out.println("Error: Ya existe un snack con el mismo nombre.");
		}

        System.out.println("Ejecutando programa...");
	}

}


