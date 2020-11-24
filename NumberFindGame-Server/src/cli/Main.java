package cli;

import Socket.GameServer;
import Socket.Server;
import util.EncryptionHelper;
import util.NumberValidException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
	static Server server;

	public static void main(String[] args) {
		EncryptionHelper encryptionHelper = new EncryptionHelper();
//		encryptionHelper.createKey(Cipher.ENCRYPT_MODE);
//		encryptionHelper.createKey(Cipher.DECRYPT_MODE);
		encryptionHelper.generateKeysForServer();

		server = new GameServer(54321);
		server.listen();

		ConsoleCommand consoleCommand = new ConsoleCommand(server);
		consoleCommand.init();
	}
}
