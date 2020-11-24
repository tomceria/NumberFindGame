package Socket;

import Socket.Encryption.ISecretObject;
import Socket.Encryption.SecretObjectImpl;
import Socket.Request.SocketRequest;
import Socket.Request.SocketRequestPackage;
import Socket.Response.SocketResponse;
import util.EncryptionHelper;
import util.NotifyingThread;

import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.UUID;

public class ClientHandler {
	UUID id;
	Socket client;
	ObjectInputStream input;
	ObjectOutputStream output;
	ClientThread clientHandleThread;

	boolean isLoggedIn = false;
	boolean isRunning = true;
	IClientIdentifier clientIdentifier; // inherits MatchPlayer
	ClientManager clientManager; // PARENT

	EncryptionHelper encryptionHelper;
	PublicKey publicKey;
	SecretKey clientSecretKey;

	public ClientHandler(Socket client, UUID id, ClientManager clientManager) throws IOException {
		this.id = id;
		this.client = client;
		this.output = new ObjectOutputStream(client.getOutputStream());
		this.input = new ObjectInputStream(client.getInputStream());

		this.clientHandleThread = new ClientThread() {
			@Override
			public void doRun() {
				try {
					while (isRunning) {
						SocketRequest requestRaw = receiveRequest();
						// RequestHandler xử lý yêu cầu BẤT ĐỒNG BỘ, trong lúc đó tiếp tục nhận yêu cầu từ client
						new RequestHandler(requestRaw, clientIdentifier, ClientHandler.this).init();
					}
				} catch (EOFException | SocketException e) {
					// Disconnect
					Logger.writeFile(String.format("Client '%s' disconnected.", ClientHandler.this.id));
					closeSocket();
				} catch (IOException | ClassNotFoundException | BadPaddingException | IllegalBlockSizeException e) {
					e.printStackTrace();
				}
			}
		};
		this.clientHandleThread.setUuid(id);
		this.clientHandleThread.addListener(clientManager); // Khi clientHandleThread kết thúc, sẽ báo cho listener (clientManager) để thực hiện xoá khỏi danh sách clientConnections
		this.clientManager = clientManager;
	}

	// Client Socket functions

	public void sendResponse(SocketResponse response) {
		if (client == null) {
			return;
		}

		try {
//			output.writeObject(sealObject(response));
			output.writeObject(encryptObject(response));
			output.flush();
		} catch (IOException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	protected SocketRequest receiveRequest() throws IOException, ClassNotFoundException, BadPaddingException, IllegalBlockSizeException {
		SocketRequest request = null;
		Object tmp = input.readObject();

		if (tmp instanceof SocketRequest) {
//			request = (SocketRequest) tmp;
		} else if (tmp instanceof SocketRequestPackage) {
//			request = ((SocketRequestPackage) tmp).data;
//			encryptedSecretKey = ((SocketRequestPackage) tmp).encryptedSecretKey;
		} else {
			throw new IOException("Invalid request object type.");
		}

		request = decryptObject(tmp);
//		request = unsealObject(input.readObject());
//		request = (SocketRequest) input.readObject();
		return request;
	}

	protected void closeSocket() {
		try {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (SocketException e) {
			System.out.println("Socket already closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// mã hóa request bằng server public key
	protected SealedObject encryptObject(Object o) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, this.publicKey);
		ISecretObject secretObject = new SecretObjectImpl((SocketRequest) o);
		SealedObject so = new SealedObject(secretObject, c);
		return so;
	}

	// giải mã response bằng client private key
	protected SocketRequest decryptObject(Object o) throws IOException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
		SealedObject s = (SealedObject) o;
		ISecretObject decryptedSecretObject = (ISecretObject) s.getObject(EncryptionHelper.DCIPHER);
		return decryptedSecretObject.getSecretRequest();
	}

	// mã hóa response trước khi gửi đi
	protected SealedObject sealObject(Object o) throws IOException, IllegalBlockSizeException {
		ISecretObject secretObject = new SecretObjectImpl((SocketResponse) o);
		SealedObject so = new SealedObject(secretObject, EncryptionHelper.CIPHER);
		return so;
	}

	// giải mã request nhận được
	protected SocketRequest unsealObject(Object o) throws ClassNotFoundException, BadPaddingException, IllegalBlockSizeException, IOException {
		SealedObject s = (SealedObject) o;
		ISecretObject decryptedSecretObject = (ISecretObject) s.getObject(EncryptionHelper.DCIPHER);
		return decryptedSecretObject.getSecretRequest();
	}

	protected void init() {
		clientHandleThread.start();
	}

	// Properties

	public UUID getId() {
		return id;
	}

	public Socket getClient() {
		return client;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public IClientIdentifier getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(IClientIdentifier clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		isLoggedIn = loggedIn;
	}

	public boolean isRunning() {
		return isRunning;
	}

	// Inner Classes

	abstract class ClientThread extends NotifyingThread {
		private UUID uuid;

		public UUID getUuid() {
			return uuid;
		}

		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}
	}
}
