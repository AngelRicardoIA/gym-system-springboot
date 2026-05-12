package com.angel.GymSystem;

import com.angel.GymSystem.model.Client;
import com.angel.GymSystem.service.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class GymSystemApplication implements CommandLineRunner {

	@Autowired
	private IClientService clientService;

	private static final Logger logger =
			LoggerFactory.getLogger(GymSystemApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args){
		logger.info("Starting Application");
		SpringApplication.run(GymSystemApplication.class, args);
		logger.info("Application completed");
	}

	@Override
	public void run(String... args) throws Exception {
		gymSystemApp();
	}

	private void gymSystemApp(){
		var exit = false;
		var sc = new Scanner(System.in);

		while (!exit){
				var option = showMenu(sc);
				exit = runOptions(sc, option);
				logger.info(nl);
		}
	}

	private static int showMenu(Scanner sc){
		logger.info("""
		\n*** GymSystem ***
		1. List clients
		2. Search client
		3. Add client
		4. Edit client
		5. Delete client
		6. Exit
                
Choose an option:\s""");

		return Integer.parseInt(sc.nextLine());
	}

	private boolean runOptions(Scanner sc, int option){
		var exit = false;
		switch (option){
			case 1 -> { //List clients
				logger.info(nl + "--- Client List ---" + nl);
				List<Client> clients = clientService.listClients();
				clients.forEach(client -> logger.info(client.toString() + nl));
			}
			case 2 -> { //Search client
				logger.info(nl + "Enter the user ID you want to search for: " + nl	);
				var idClient = Integer.parseInt(sc.nextLine());
				Client client = clientService.searchClientByID(idClient);
				if(client != null){
					logger.info("Client found: " + client + nl);
				}
				else {
					logger.info("The client was NOT found " + client + nl);
				}
			}
			case 3 -> { //Add client
				logger.info("--- Add Client ---" + nl);
				logger.info("Name: ");
				var name = sc.nextLine();
				logger.info("Last Name: ");
				var lastName = sc.nextLine();
				logger.info("Membership: ");
				var membership = Integer.parseInt(sc.nextLine());

				//“Create the client object
				var client = new Client();
				client.setNombre(name);
				client.setApellido(lastName);
				client.setMembresia(membership);
				clientService.saveClient(client);
				logger.info("Client added: " + client + nl);
			}
			case 4 -> { //Edit Client
				logger.info("--- Edit Client ---" + nl);
				logger.info("ID: ");
				var idClient = Integer.parseInt(sc.nextLine());
				Client client = clientService.searchClientByID(idClient);

				if (client != null){
					logger.info("Name: ");
					var name = sc.nextLine();
					logger.info("Last Name: ");
					var lastName = sc.nextLine();
					logger.info("Membership: ");
					var membership = Integer.parseInt(sc.nextLine());
					client.setNombre(name);
					client.setApellido(lastName);
					client.setMembresia(membership);
					clientService.saveClient(client);
					logger.info("Client updated: " + client + nl);
				}
				else{
					logger.info("The Client was not found: " + client + nl);
				}
			}
			case 5 -> { //Delete client
				logger.info("--- Delete client ---");
				logger.info("ID: ");
				var idClient = Integer.parseInt(sc.nextLine());
				var client = clientService.searchClientByID(idClient);
				if(client != null){
					clientService.deleteClient(client);
					logger.info("Client deleted: " + client + nl);
				}
				else {
					logger.info("Failed to delete the client: " + client + nl);
				}
			}
			case 6 -> { //Exit
				logger.info("See you later..." + nl + nl);
				exit = true;
			}
			default -> logger.info("Invalid option: " + option + nl);
		}
		return exit;
	}
}
