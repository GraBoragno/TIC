package uy.edu.um.proyectoTIC;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import uy.edu.um.proyectoTIC.entities.Combo;
import uy.edu.um.proyectoTIC.entities.Film;
import uy.edu.um.proyectoTIC.entities.Snack;
import uy.edu.um.proyectoTIC.entities.Ticket;
import uy.edu.um.proyectoTIC.entities.users.Admin;
import uy.edu.um.proyectoTIC.exceptions.*;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.exceptions.DuplicateEntityException;
import uy.edu.um.proyectoTIC.exceptions.EntityNotFoundException;
import uy.edu.um.proyectoTIC.repository.SnackRepository;
import uy.edu.um.proyectoTIC.services.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	@Autowired
	private TicketService ticketService;


	public static void main(String[] args) throws DuplicateEntityException {
		ApplicationContext ctx = SpringApplication.run(TicApplicationProyect.class, args);
		TicApplicationProyect app = ctx.getBean(TicApplicationProyect.class);
		app.runInCommandLine();
	}

	public void runInCommandLine() throws DuplicateEntityException {
//		Client newClient = clientService.addClient("email@email.com","persona1","xd", "21/10/2024","123456789");

//		System.out.println("----------------1-------------------------");
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
//		System.out.println("----------------2-------------------------");
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
//		System.out.println("----------------3-------------------------");
//
//		try {
//			roomService.createRoom(1, 1);
//			roomService.createRoom(2, 1);
//			roomService.createRoom(1, 10);
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error: El cine no existe");
//        }
//
//		System.out.println("----------------4-------------------------");
//
//		try {
////			snackService.addSnack("Pop dulce", "200");
////			snackService.addSnack("Nachos", "250");
//			snackService.addSnack("Pop salado", "150");
////			snackService.addSnack("Nachos", "300");
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe un snack con el mismo nombre.");
//		}
//
//		System.out.println("----------------5-------------------------");
//
//		try {
//			seatService.fullSeats();
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe ese asiento");
//		}
//
//		System.out.println("----------------6-------------------------");
//
//		try {
//			List<Snack> allSnacks = snackRepository.findAll();
//
//			Combo combo1 = comboService.addCombo(500, allSnacks, "Todos los snacks");
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error: No se encontraron algunos snacks.");
//		}
//
//
//		System.out.println("----------------7-------------------------");
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
//			roomService.createRoom(1, 3);
//			roomService.createRoom(3, 4);
//			roomService.createRoom(2, 2);
//			roomService.createRoom(10, 1);
//			roomService.createRoom(1, 1);
//		} catch (EntityNotFoundException | InvalidRoomQtyException e) {
//			System.out.println("Error: El cine no existe");
//		}
//
//		System.out.println("----------------8-------------------------");
//
//		try {
////			broadcastService.addBroadcast("10/11/2024 18:00", 250, 1, 1, 1);
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
//
//		System.out.println("----------------9-------------------------");
//
//		try {
//			filmService.addFilm("Parasite", "Bong Joon-ho", 132, "2019", "drama, thriller");
//			filmService.addFilm("Pulp Fiction", "Quentin Tarantino", 154, "1994", "crimen, drama");
//			filmService.addFilm("Fight Club", "David Fincher", 139, "1999", "drama, thriller");
//			filmService.addFilm("The Godfather", "Francis Ford Coppola", 175, "1972", "crimen, drama");
//
//		} catch (DuplicateEntityException | EntityNotFoundException e) {
//			System.out.println("Error: Ya existe una pelicula con ese nombre.");
//		}
//
//		System.out.println("----------------10-------------------------");
//
//		Optional<Film> parasite = filmService.findByName("Parasite");
//        Optional<Film> pulpFiction = filmService.findByName("Pulp Fiction");
//        Optional<Film> fightClub = filmService.findByName("Fight Club");
//        Optional<Film> theGodfather = filmService.findByName("The Godfather");
//
//		System.out.println("----------------11-------------------------");
//
//		parasite.ifPresent(adminService::deleteFilm);
//        pulpFiction.ifPresent(adminService::deleteFilm);
//        fightClub.ifPresent(adminService::deleteFilm);
//        theGodfather.ifPresent(adminService::deleteFilm);
//
//		System.out.println("----------------12-------------------------");
//
//		Optional<Film> parasite2 = filmService.findByName("Parasite");
//		Optional<Film> pulpFiction2 = filmService.findByName("Pulp Fiction");
//		Optional<Film> fightClub2 = filmService.findByName("Fight Club");
//		Optional<Film> theGodfather2 = filmService.findByName("The Godfather");
//
//		System.out.println("----------------13-------------------------");
//
//		filmService.rateFilm(5, 5);
//		filmService.rateFilm(1, 5);
//		filmService.rateFilm(1, 1);
//		filmService.rateFilm(1, 5);

//		Crear ticket
//        List<Snack> allSnacks = snackRepository.findAll();
//        List<Combo> allCombos = comboService.getAvailableCombos();
//        ticketService.buyTicket(1, 1, 1, "email@email.com",  allCombos, allSnacks);
//

//        try {
//			Client client = clientService.findByEmail("email@email.com");
//			List<Ticket> tickets = clientService.getTicketByEmail("email@email.com");
//			tickets = clientService.getTicketByEmail(client.getEmail());
//			System.out.println(tickets.get(1));
//
//
//        } catch (EntityNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//		System.out.println("------------------14----------------------");
//		try {
//			filmService.addFilm("My Neighbor Totoro", "Hayao Miyazaki", 86, "1988", "anime, familia, fantasía");
//			filmService.addFilm("Princess Mononoke", "Hayao Miyazaki", 134, "1997", "anime, fantasía, aventura");
//			filmService.addFilm("Howl's Moving Castle", "Hayao Miyazaki", 119, "2004", "anime, fantasía, romance");
//			filmService.addFilm("Castle in the Sky", "Hayao Miyazaki", 124, "1986", "anime, aventura, fantasía");
//			filmService.addFilm("Kiki's Delivery Service", "Hayao Miyazaki", 103, "1989", "anime, familia, aventura");
//			filmService.addFilm("The Tale of the Princess Kaguya", "Isao Takahata", 137, "2013", "anime, drama, fantasía");
//			filmService.addFilm("Ponyo", "Hayao Miyazaki", 101, "2008", "anime, familia, fantasía");
//			filmService.addFilm("Whisper of the Heart", "Yoshifumi Kondō", 111, "1995", "anime, romance, drama");
//			filmService.addFilm("Hereditary", "Ari Aster", 127, "2018", "terror, drama, suspenso");
//			filmService.addFilm("Get Out", "Jordan Peele", 104, "2017", "terror, misterio, suspenso");
//			filmService.addFilm("The Shining", "Stanley Kubrick", 146, "1980", "terror, misterio, suspenso");
//			filmService.addFilm("It", "Andy Muschietti", 135, "2017", "terror, suspenso");
//			filmService.addFilm("Toy Story", "John Lasseter", 81, "1995", "animación, comedia, aventura");
//			filmService.addFilm("Inside Out", "Pete Docter", 95, "2015", "animación, comedia, drama");
//			filmService.addFilm("Coco", "Lee Unkrich, Adrian Molina", 105, "2017", "animación, familia, fantasía");
//			filmService.addFilm("Shrek", "Andrew Adamson, Vicky Jenson", 90, "2001", "animación, comedia, aventura");
//
//		} catch (DuplicateEntityException | EntityNotFoundException e) {
//			System.out.println("Error: Ya existe una pelicula con ese nombre.");
//		}

//		try {
//			roomService.createRoom(4, 7);
//			roomService.createRoom(4, 3);
//			roomService.createRoom(5, 1);
//			roomService.createRoom(3, 2);
//		} catch (EntityNotFoundException | InvalidRoomQtyException e) {
//			System.out.println("Error: El cine no existe");
//		}

//		try {
//			broadcastService.addBroadcast("19/11/2024 17:00", 250, 2, 1, 1);
//			broadcastService.addBroadcast("19/11/2024 19:30", 300, 8, 1, 7);
//			broadcastService.addBroadcast("19/11/2024 21:00", 220, 4, 3, 3);

//			broadcastService.addBroadcast("20/11/2024 16:00", 200, 5, 1, 8);
//			broadcastService.addBroadcast("20/11/2024 18:30", 280, 1, 3, 2);
//			broadcastService.addBroadcast("20/11/2024 20:00", 250, 3, 2, 14);
//
//			broadcastService.addBroadcast("21/11/2024 15:30", 220, 2, 1, 4);
//			broadcastService.addBroadcast("21/11/2024 18:00", 275, 8, 1, 5);
//			broadcastService.addBroadcast("21/11/2024 20:30", 260, 10, 1, 28);
//
//			broadcastService.addBroadcast("22/11/2024 14:00", 300, 4, 3, 19);
//			broadcastService.addBroadcast("22/11/2024 17:00", 240, 5, 1, 22);
//			broadcastService.addBroadcast("22/11/2024 19:30", 280, 3, 2, 6);
//
//			broadcastService.addBroadcast("23/11/2024 16:30", 220, 2, 1, 15);
//			broadcastService.addBroadcast("23/11/2024 19:00", 275, 8, 1, 14);
//			broadcastService.addBroadcast("23/11/2024 21:30", 300, 4, 3, 13);
//
//			broadcastService.addBroadcast("24/11/2024 15:00", 200, 1, 3, 16);
//			broadcastService.addBroadcast("24/11/2024 18:30", 250, 10, 1, 19);
//			broadcastService.addBroadcast("24/11/2024 20:45", 280, 5, 1, 17);
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error al agregar la emisión: " + e.getMessage());
//		}
//
//		try {
//			snackService.addSnack("Pancho", "350");
//			snackService.addSnack("Papas Fritas", "200");
//			snackService.addSnack("Chocolate", "250");
//			snackService.addSnack("Helado", "300");
//			snackService.addSnack("Caramelos", "100");
//			snackService.addSnack("Pizza", "400");
//			snackService.addSnack("Coca comun", "150");
//			snackService.addSnack("Coca light", "150");
//			snackService.addSnack("Coca zero", "150");
//			snackService.addSnack("Sprite zero", "150");
//			snackService.addSnack("Fanta", "150");
//			snackService.addSnack("Agua", "100");
//			snackService.addSnack("Jugo", "200");
//			snackService.addSnack("Galletas", "150");
//			snackService.addSnack("Agua con gas", "100");
//		} catch (DuplicateEntityException e) {
//			System.out.println("Error: Ya existe un snack con el mismo nombre.");
//		}

//		try {
//			List<Snack> basicComboSnacks = Arrays.asList(
//					snackRepository.findByName("Pop dulce").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Pop dulce")),
//					snackRepository.findByName("Coca comun").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Coca comun"))
//			);
//			Combo combo2 = comboService.addCombo(300, basicComboSnacks, "Combo 2");
//
//			List<Snack> familyComboSnacks = Arrays.asList(
//					snackRepository.findByName("Pizza").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Pizza")),
//					snackRepository.findByName("Papas Fritas").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Papas Fritas")),
//					snackRepository.findByName("Coca light").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Coca light"))
//			);
//			Combo combo3 = comboService.addCombo(700, familyComboSnacks, "Combo 3");
//
//			List<Snack> sweetComboSnacks = Arrays.asList(
//					snackRepository.findByName("Chocolate").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Chocolate")),
//					snackRepository.findByName("Caramelos").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Caramelos")),
//					snackRepository.findByName("Sprite zero").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Sprite zero"))
//			);
//			Combo combo4 = comboService.addCombo(400, sweetComboSnacks, "Combo 4");
//
//			List<Snack> refreshingComboSnacks = Arrays.asList(
//					snackRepository.findByName("Agua").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Agua")),
//					snackRepository.findByName("Coca light").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Coca light")),
//					snackRepository.findByName("Fanta").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Fanta"))
//			);
//			Combo combo5 = comboService.addCombo(250, refreshingComboSnacks, "Combo 5");
//
//			List<Snack> classicComboSnacks = Arrays.asList(
//					snackRepository.findByName("Nachos").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Nachos")),
//					snackRepository.findByName("Jugo").orElseThrow(() -> new EntityNotFoundException("Snack no encontrado: Jugo"))
//			);
//			Combo combo6 = comboService.addCombo(350, classicComboSnacks, "Combo 6");
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error: No se encontraron algunos snacks.");
//		}

//		try {
//			broadcastService.addBroadcast("19/11/2024 13:30", 250, 2, 1, 1);
//			broadcastService.addBroadcast("19/11/2024 13:30", 250, 1, 7, 1);
//			broadcastService.addBroadcast("19/11/2024 19:30", 300, 1, 1, 7);
//
//		} catch (EntityNotFoundException e) {
//			System.out.println("Error al agregar la emisión: " + e.getMessage());
//		}

        System.out.println("Ejecutando programa...");
    }

}


