package Socket;

import util.IThreadCompleteListener;
import util.NotifyingThread;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

public class ClientManager implements IThreadCompleteListener {
    HashMap<UUID, ClientHandler> clientConnections = new HashMap<UUID, ClientHandler>();

    public HashMap<UUID, ClientHandler> getClientConnections() {
        return clientConnections;
    }

    protected void addAndStartClient(Socket client) {      // Nhận tham biến là Socket client được Server instance accept()
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
    public void notifyOfThreadComplete(Runnable thread) {
        UUID clientHandlerId = ((ClientHandler.ClientThread) thread).getUuid();
        clientConnections.remove(clientHandlerId);
    }
}
