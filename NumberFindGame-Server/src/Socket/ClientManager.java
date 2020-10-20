package Socket;

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

    protected void addAndStartClient(Socket client) {   // Nhận tham biến là Socket client được Server instance accept()
        try {
            UUID clientHandlerId = UUID.randomUUID();           // UUID này được gắn liền với ClientHandler.ClientThread
            ClientHandler clientHandler = new ClientHandler(client, clientHandlerId, this);
            clientHandler.init();                                     // Khởi động Thread mới duy trì kết nối với Client
            clientConnections.put(clientHandlerId, clientHandler);  // Thêm vào danh sách clientConnections để quản lý sau này
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyOfThreadComplete(Runnable thread) {  // Chạy khi ClientHandler.clientHandleThread hoàn tất công việc run()
        UUID clientHandlerId = ((ClientHandler.ClientThread) thread).getUuid();
        clientConnections.remove(clientHandlerId);                // Xoá khỏi danh sách người chơi hiện tại trong SERVER
        ((GameServer) server).getGameRooms().get(0).getPlayerClients().remove(clientHandlerId); // Xoá khỏi danh sách người chơi hiện tại trong PHÒNG
    }

    // Properties

    public Server getServer() {
        return server;
    }

    public HashMap<UUID, ClientHandler> getClientConnections() {
        return clientConnections;
    }

}
