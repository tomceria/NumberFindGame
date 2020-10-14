package Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.UUID;

public class Server {
    Thread serverThread;
    ServerSocket serverSocket;
    HashMap<UUID, ClientHandler> clientConnections = new HashMap<UUID, ClientHandler>();
    boolean isRunning = false;

    public Server(int port) {
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    isRunning = true;
                    System.out.println(String.format("Server is running on port %s", port));
                    while (isRunning) {
                        Socket client = serverSocket.accept();
                        ClientHandler clientHandler = new ClientHandler(client);
                        clientHandler.start();
                        clientConnections.put(UUID.randomUUID(), clientHandler);
                        System.out.println("Current connections: ");
                        for (UUID key : clientConnections.keySet()) {
                            System.out.println(key + ": " + clientConnections.get(key).client.getInetAddress().toString());
                        }
                    }

                } catch (SocketException e) {       // Sẽ chạy vào Exception này sau khi có class khác gọi server.halt()
                    System.out.println("Shutting down server.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void listen() {
        serverThread.start();
    }

    public void halt() {
        this.isRunning = false;

        try {
            for (ClientHandler clientHandler : clientConnections.values()) {
                clientHandler.closeSocket();
            }
            clientConnections.clear();

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}