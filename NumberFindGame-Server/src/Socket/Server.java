package Socket;

import com.mysql.cj.xdevapi.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class Server {
    static HashMap<UUID, ClientHandler> clientConnections = new HashMap<UUID, ClientHandler>();

    // TODO: Placeholder - REMOOOOOVE
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(54321);
            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                clientHandler.start();
                clientConnections.put(UUID.randomUUID(), clientHandler);
                System.out.println("Current connections: ");
                for (UUID key : clientConnections.keySet()) {
                    System.out.println(key + ": " + clientConnections.get(key).client.getInetAddress().toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}