package uy.edu.um.proyectoTIC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.exceptions.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

	@Autowired
	private SeatService seatService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private SnackRepository snackRepository;

    @Autowired
    private AdminService adminService;

	@Autowired
	private BroadcastService broadcastService;


	public static void main(String[] args) throws DuplicateEntityException{
		ApplicationContext ctx = SpringApplication.run(TicApplicationProyect.class, args);
		TicApplicationProyect app = ctx.getBean(TicApplicationProyect.class);
		app.runInCommandLine();
	}

	public void runInCommandLine() throws DuplicateEntityException {
//		Client newClient = clientService.addClient("email@email.com","persona1","xd", "21/10/2024","123456789");
//
//		try {
//			filmService.addFilm("The Grand Budapest Hotel", "Wes Anderson", 99, "2014", "comedia, drama");
//			filmService.addFilm("Blade Runner 2049", "Denis Villeneuve", 164, "2017", "ciencia ficción, misterio");
//			filmService.addFilm("The Dark Knight", "Christopher Nolan", 152, "2008", "acción, thriller");
//			filmService.addFilm("Dune", "Denis Villeneuve", 155, "2021", "ciencia ficción, aventura");
//			filmService.addFilm("Dune: Part Two", "Denis Villeneuve", 160, "2023", "ciencia ficción, aventura");
//			filmService.addFilm("Inception", "Christopher Nolan", 148, "2010", "ciencia ficción, thriller");
//			filmService.addFilm("The Matrix", "Wachowski Sisters", 136, "1999", "acción, ciencia ficción");
//			filmService.addFilm("Interstellar", "Christopher Nolan", 169, "2014", "ciencia ficción, drama");
//
//		} catch (DuplicateEntityException | EntityNotFoundException e) {
//			System.out.println("Error: Ya existe una pelicula con ese nombre.");
//		}
//
//		try {
//			cinemaService.addCinema(1, 8, "Punta Carretas");
//			cinemaService.addCinema(2, 5, "Ciudad Vieja");
//			cinemaService.addCinema(3, 7, "Pocitos");
//			cinemaService.addCinema(4, 4, "Carrasco");
//			cinemaService.addCinema(5, 6, "Tres Cruces");
//			cinemaService.addCinema(6, 10, "Centro");
//			cinemaService.addCinema(7, 3, "Malvín");
//			cinemaService.addCinema(8, 6, "Buceo");
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe un cine con los mismos datos.");
//		}
//
//		try {
//			roomService.createRoom(1, 1);
//			roomService.createRoom(2, 1);
//			roomService.createRoom(1, 10);
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error: El cine no existe");
//        }
//
//		try {
//			snackService.addSnack("Pop dulce", "200");
//			snackService.addSnack("Nachos", "250");
//			snackService.addSnack("Pop salado", "150");
//			snackService.addSnack("Nachos", "300");
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe un snack con el mismo nombre.");
//		}
//
//		try {
//			seatService.fullSeats();
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe ese asiento");
//		}
//
//
//		try {
//			List<Snack> allSnacks = snackRepository.findAll();
//
//			Combo combo1 = comboService.addCombo(500, allSnacks);
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error: No se encontraron algunos snacks.");
//		}
//
//
//
//		Admin admin = adminService.createAdmin("admin1@wtf.com","Pablo","casa de pablo","10/10/2000","pablo1234");
//
//		for (int i = 0; i < filmService.getAvailableFilms().size(); i++) {
//			System.out.println(filmService.getAvailableFilms().get(i).getFilmName());
//		}
//
//		try {
//			roomService.createRoom(8, 1);
//			roomService.createRoom(1, 7);
//			roomService.createRoom(8, 1);
//			roomService.createRoom(3, 4);
//			roomService.createRoom(2, 2);
//			roomService.createRoom(10, 1);
//			roomService.createRoom(1, 1);
//		} catch (EntityNotFoundException | InvalidRoomQtyException e) {
//			System.out.println("Error: El cine no existe");
//		}
//
//		try {
//			broadcastService.addBroadcast("10/11/2024 18:00", 250, 1, 1, 1);
//			broadcastService.addBroadcast("10/11/2024 20:30", 300, 1, 7, 2);
//			broadcastService.addBroadcast("11/11/2024 19:00", 200, 1, 3, 3);
//			broadcastService.addBroadcast("11/11/2024 21:30", 280, 8, 1, 4);
//			broadcastService.addBroadcast("12/11/2024 17:00", 300, 3, 4, 5);
//			broadcastService.addBroadcast("12/11/2024 20:30", 250, 1, 1, 6);
//			broadcastService.addBroadcast("13/11/2024 18:00", 220, 2, 2, 7);
//			broadcastService.addBroadcast("13/11/2024 21:30", 280, 10, 1, 8);
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error al agregar la emisión: " + e.getMessage());
//		}



		System.out.println("Ejecutando programa...");
	}

}


