package cli;

import Socket.GameServer;
import Socket.Server;
import util.EncryptionHelper;

import javax.crypto.Cipher;

public class Main {
	static Server server;

	public static void main(String[] args) {
		EncryptionHelper encryptionHelper = new EncryptionHelper();
		encryptionHelper.createKey(Cipher.ENCRYPT_MODE);
		encryptionHelper.createKey(Cipher.DECRYPT_MODE);

		server = new GameServer(54321);
		server.listen();

		ConsoleCommand consoleCommand = new ConsoleCommand(server);
		consoleCommand.init();
	}
}
