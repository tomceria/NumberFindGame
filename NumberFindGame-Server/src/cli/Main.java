package cli;

import Socket.GameServer;
import Socket.Server;
import util.NumberValidException;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static Server server;
	static boolean isCommanding = true;

	public static void main(String[] args) throws NumberValidException {

//		JsonHelper jsonHelper = new JsonHelper();
//		MatchConfig matchConfig =  jsonHelper.readConfig();
//		matchConfig.setNumberQty(550);
//		jsonHelper.saveConfig(matchConfig);

		server = new GameServer(54321);
		server.listen();

		ConsoleCommand consoleCommand = new ConsoleCommand(server);
		consoleCommand.init();
	}
}
