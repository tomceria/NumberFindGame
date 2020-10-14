package cli;

import Socket.Server;

import java.util.Scanner;

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
				case "exit": {
					server.halt();
					isCommanding = false;
				}
			}
		} while (isCommanding);
	}

}
