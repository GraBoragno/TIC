package uy.edu.um.proyectoTIC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import uy.edu.um.proyectoTIC.entities.users.Client;
import uy.edu.um.proyectoTIC.repository.ClientRepository;
import uy.edu.um.proyectoTIC.services.ClientService;

import java.time.LocalDate;

@SpringBootApplication
@Component
@EnableJpaRepositories(basePackages = "uy.edu.um.proyectoTIC.repository")
@EntityScan(basePackages = "uy.edu.um.proyectoTIC.entities")
public class TicApplicationProyect {

	@Autowired
	private ClientService clientService;

	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(TicApplicationProyect.class, args);
		TicApplicationProyect app = ctx.getBean(TicApplicationProyect.class);
		app.runInCommandLine();
	}

	public void runInCommandLine() {
		Client newClient = clientService.addClient("email@email.com","persona1","xd", "21/10/2024","1234");

		System.out.println("Ejecutando programa...");
	}
}


