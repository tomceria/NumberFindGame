package Socket;

import java.util.HashMap;
import java.util.UUID;

public class RequestHandler {
    Thread requestHandleThread;
    IClientIdentifier clientIdentifier;
    ClientHandler clientHandler;
    SocketRequest requestRaw;

    public RequestHandler(SocketRequest requestRaw, IClientIdentifier clientIdentifier, ClientHandler clientHandler) {
        this.requestRaw = requestRaw;
        this.clientIdentifier = clientIdentifier;
        this.clientHandler = clientHandler;
        this.requestHandleThread = new Thread() {
            @Override
            public void run() {
                if (requestRaw.getMessage() != null) {
                    System.out.println(requestRaw.getMessage());
                }

                switch (requestRaw.getAction()) {
                    case MESSAGE: {
                        System.out.println("Received message: " + requestRaw.getMessage());
                        GameServer server = ((GameServer) (clientHandler.getClientManager().getServer()));
                        HashMap<UUID, ClientHandler> playerClients = server.getGameRooms().get(0).getPlayerClients();

                        SocketResponse response = new SocketResponse(SocketResponse.Status.SUCCESS, "Current players count: " + playerClients.size());
                        server.broadcast(response);
                    }
                    case DISCONNECT: {
//                        response = new SocketResponse(SocketResponse.Status.END);
//                        output.writeObject(response);
//                        closeSocket();
                        break;
                    }
                }
            }
        };
    }

    public void init() {
        requestHandleThread.start();
    }
}
