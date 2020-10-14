package cli;

import Socket.Server;

import java.util.Scanner;
import java.util.UUID;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static Server server;
	static boolean isCommanding = true;

	public static void main(String[] args) {

		// PlayerBUS playerBus = new PlayerBUS();
		// System.out.print(playerBus.login("saidan00", "123"));

		// Start server
		server = new Server(54321);
		server.listen();

		do {
			String command = scan.nextLine();
			switch (command) {
				case "list": {
					System.out.println("Current connections: ");
					for (UUID key : server.getClientConnections().keySet()) {
						System.out.println(key + ": " +
								server.getClientConnections()
										.get(key)
										.getClient()
										.getInetAddress()
										.toString());
					}
					break;
				}
				case "exit": {
					server.halt();
					isCommanding = false;
					break;
				}
			}
		} while (isCommanding);
	}

}
