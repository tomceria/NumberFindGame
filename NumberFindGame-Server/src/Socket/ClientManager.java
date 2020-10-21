package Socket;

import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;
import util.IThreadCompleteListener;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ClientManager implements IThreadCompleteListener {
    Server server; // PARENT
    HashMap<UUID, ClientHandler> clientConnections = new HashMap<UUID, ClientHandler>(); // Server instance's current active players

    public ClientManager(Server server) {
        this.server = server;
    }

    // Methods


    public void sendResponseToClient(UUID clientHandlerId, SocketResponse response) {
        ClientHandler clientHandler = clientConnections.get(clientHandlerId);
        clientHandler.sendResponse(response);
    }

    public void sendResponseToBulkClients(HashMap<UUID, ClientHandler> clientConnections, SocketResponse response) {
        for (ClientHandler clientHandler : clientConnections.values()) {
            clientHandler.sendResponse(response);
        }
    }

    public void broadcastResponse(SocketResponse response) {
        sendResponseToBulkClients(this.clientConnections, response);
    }

    protected void addAndStartClient(Socket client) {   // Nhận tham biến là Socket client được Server instance accept()

        try {
            UUID clientHandlerId = UUID.randomUUID();           // UUID này được gắn liền với ClientHandler.ClientThread
            System.out.println(String.format("Login detected. Client ID %s; IP: %s", clientHandlerId, client.getInetAddress().toString()));
            ClientHandler clientHandler = new ClientHandler(client, clientHandlerId, this);
            clientHandler.init();                                     // Khởi động Thread mới duy trì kết nối với Client
            clientConnections.put(clientHandlerId, clientHandler);  // Thêm vào danh sách clientConnections để quản lý sau này
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void disconnectClient(UUID clientHandlerId) {
        clientConnections.remove(clientHandlerId);                // Xoá khỏi danh sách người chơi hiện tại trong SERVER
        ((GameServer) server).getGameRooms().get(0).getPlayerClients().remove(clientHandlerId); // TODO: Game Business logic. Xoá khỏi danh sách người chơi hiện tại trong PHÒNG
    }

    protected SocketRequest receiveRequestFromClient(UUID clientHandlerId) {
        ClientHandler clientHandler = clientConnections.get(clientHandlerId);
        SocketRequest request = null;
        try {
            request = clientHandler.receiveRequest();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }

    // Overrides

    @Override
    public void notifyOfThreadComplete(Runnable thread) {  // Chạy khi ClientHandler.clientHandleThread hoàn tất công việc run()
        UUID clientHandlerId = ((ClientHandler.ClientThread) thread).getUuid();
        disconnectClient(clientHandlerId);
    }

    // Properties

    public Server getServer() {
        return server;
    }

    public HashMap<UUID, ClientHandler> getClientConnections() {
        return clientConnections;
    }

}
