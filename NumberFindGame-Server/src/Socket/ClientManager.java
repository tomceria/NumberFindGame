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

    public void addAndStartClient(Socket client) {
        try {
            UUID clientHandlerId = UUID.randomUUID();
            ClientHandler clientHandler = new ClientHandler(client, clientHandlerId, this);
            clientHandler.init();
            clientConnections.put(clientHandlerId, clientHandler);
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
