package Socket;

import Socket.Response.SocketResponse;

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
        clientManager = new ClientManager(this);           // ClientManager quản lý các Client kết nối đến server
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    isRunning = true;                         // isRunning true chỉ khi ServerSocket khởi tạo thành công
                    System.out.println(String.format("Server is running on port %s", port));  // TODO: Xuất console qua 1 class chuyên dụng
                    while (isRunning) {                        // isRunning được gán false tại halt() => Kết thúc Thread
                        Socket client = serverSocket.accept();                                  // Chờ kết nối từ Client
                        clientManager.addAndStartClient(client);
                    }

                } catch (SocketException e) {       // Sẽ chạy vào Exception này sau khi có class KHÁC gọi server.halt()
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
        this.clientManager.broadcastResponse(new SocketResponse(SocketResponse.Status.SUCCESS, SocketResponse.Action.NET_CLOSE, "Server closed."));

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