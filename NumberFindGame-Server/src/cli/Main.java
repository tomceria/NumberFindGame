package cli;

import Socket.GameServer;
import Socket.Server;
import util.NumberValidException;

public class Main {
	static Server server;

	public static void main(String[] args) throws NumberValidException {
		server = new GameServer(54321);
		server.listen();

		ConsoleCommand consoleCommand = new ConsoleCommand(server);
		consoleCommand.init();
	}
}
