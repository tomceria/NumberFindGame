package Socket;

import dto.PlayerDTO;

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
                        PlayerDTO player = (PlayerDTO) clientHandler.getClientIdentifier();
//                        ((GameServer)(clientHandler.getClientManager().getServer())).gameRoom.joinRoom(player);
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
