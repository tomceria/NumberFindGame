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
    ClientManager clientManager;
    boolean isRunning = false;

    public Server(int port) {
        clientManager = new ClientManager();
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    isRunning = true;
                    System.out.println(String.format("Server is running on port %s", port));
                    while (isRunning) {
                        Socket client = serverSocket.accept();
                        clientManager.addAndStartClient(client);

                    }

                } catch (SocketException e) {       // Sẽ chạy vào Exception này sau khi có class khác gọi server.halt()
                    System.out.println("Shutting down server.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void listen() {
        serverThread.start();
    }

    public void halt() {
        this.isRunning = false;
        HashMap<UUID, ClientHandler> clientConnections = this.clientManager.getClientConnections();

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