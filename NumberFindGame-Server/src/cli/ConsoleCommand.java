package cli;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

import Socket.ClientHandler;
import Socket.Server;
import bus.PlayerBUS;
import dto.MatchPlayer;
import dto.PlayerDTO;
import util.Display;

public class ConsoleCommand {

	// TODO Auto-generated method stub
	private Server server; // PARENT
	static Scanner scan = new Scanner(System.in);

	Display tableDisplay = new Display();

	private String command;
	private String option1;
	private String option2;

	public ConsoleCommand(Server server) {
		this.server = server;

	}

	public void handleCommand(String inputCommand) {
		this.option1 = "";
		this.option2 = "";
		String s[] = inputCommand.trim().split(" ");
		// TODO check null
		this.command = s[0];
		if (s.length >= 2) {
			option1 = s[1];
		}
		if (s.length >= 3) {
			option2 = s[2];
		}
	}

	public void init() {
		boolean isCommanding = true;

		do {
			System.out.print("Input: ");
			String inputCommand = scan.nextLine();
			handleCommand(inputCommand);
			switch (command) {
			case "list": {
				list();
				break;
			}
			case "config": {
				config();
				break;
			}
			case "exit": {
				server.halt();
				isCommanding = false;
				break;
			}
			case "command": {
				System.out.println("Command: list <option>\n" + "\t config <option> <value>\t");
				break;
			}
			default: {
				System.out.println("Syntax error");
				break;
			}
			}
		} while (isCommanding);
	}

	public void list() {
		switch (option1) {
		case "user": {
			getUsers();
			break;
		}
		case "online": {
			getOnlineUsers();
			break;
		}
		default: {
			System.out.println("Command: list <option>\n" + "\t user\t Show list of registered users\n"
					+ "\t online\t Show list of active users");
			break;
		}
		}
	}

	public void config() {
		switch (option1) {
		case "numberQty": {

			break;
		}
		case "time": {

			break;
		}
		case "maxPlayer": {

			break;
		}
		default: {
			System.out.println(
					"Command: config <option> <value>\n" + "\t numberQty <1-900>\t Config game number quantity\n"
							+ "\t time <1000-3600000>\t Config game time"
							+ "\t maxPlayer <2-8>\t Config game max number of player");
			break;
		}
		}
	}

	public void getUsers() {
		PlayerBUS playerBus = new PlayerBUS();
		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();
		players = playerBus.getAll();
		if (players.isEmpty()) {
			System.out.println("No user");
			return;
		}

		String[] table = new String[] { "Username", "Email", "First Name", "Last Name" };
		List<String[]> tableList = new ArrayList<>();
		tableList.add(table);
		for (PlayerDTO player : players) {
			table = new String[] { player.getUsername(), player.getEmail(), player.getFirstName(),
					player.getLastName() };
			tableList.add(table);
		}
		tableDisplay.tableDisplay(tableList);

	}

	public void getOnlineUsers() {

		HashMap<UUID, ClientHandler> clientConections = this.server.getClientManager().getClientConnections();
		UUID clientHandlerID;
		Socket clientSocket;
		PlayerDTO onlinePlayer;
		if (clientConections.size() == 0) {
			System.out.println("No user is online");
			return;
		}
		String[] table = new String[] { "Username", "Connection UUID", "IP" };
		List<String[]> tableList = new ArrayList<>();
		tableList.add(table);
		for (ClientHandler clientHandler : clientConections.values()) {
			onlinePlayer = ((MatchPlayer) clientHandler.getClientIdentifier()).getPlayer();
			clientHandlerID = clientHandler.getId();
			clientSocket = clientHandler.getClient();

			table = new String[] { onlinePlayer.getUsername(), clientHandlerID.toString(),
					clientSocket.getInetAddress().getHostAddress() };
			tableList.add(table);
		}
		tableDisplay.tableDisplay(tableList);
	}

}
