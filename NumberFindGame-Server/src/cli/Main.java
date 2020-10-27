package cli;

import Socket.GameServer;
import Socket.Server;
import dto.MatchConfig;
import util.JsonHelper;
import util.NumberValidException;

import java.util.Scanner;
import java.util.UUID;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static Server server;
	static boolean isCommanding = true;

	public static void main(String[] args) throws NumberValidException {

//		JsonHelper jsonHelper = new JsonHelper();
//		MatchConfig matchConfig =  jsonHelper.readConfig();
//		matchConfig.setNumberQty(550);
//		jsonHelper.saveConfig(matchConfig);

		// PlayerBUS playerBus = new PlayerBUS();
		// System.out.print(playerBus.login("saidan00", "123"));

		// Start server
		server = new GameServer(54321);
		server.listen();

		do {
			String command = scan.nextLine();
			switch (command) {
				case "list": {
					System.out.println("Current connections: ");
					for (UUID key : server.getClientManager().getClientConnections().keySet()) {
						System.out.println(key + ": " +
								server.getClientManager().getClientConnections()
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
