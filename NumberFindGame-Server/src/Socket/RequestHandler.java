package Socket;

import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;

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
                switch (requestRaw.getAction()) {
                    case MESSAGE: {
                        System.out.println("Received message: " + requestRaw.getMessage());
//                        GameServer server = ((GameServer) (clientHandler.getClientManager().getServer()));
//                        HashMap<UUID, ClientHandler> playerClients = server.getGameRooms().get(0).getPlayerClients();
//
//                        SocketResponse response = new SocketResponse(SocketResponse.Status.SUCCESS, "Current players count: " + playerClients.size());
//                        server.broadcast(response);
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
