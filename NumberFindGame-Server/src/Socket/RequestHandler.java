package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_SubmitLevelNode;

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
                    case MSG: {
                        System.out.println("Received message: " + requestRaw.getMessage());
                        break;
                    }
                    case GAME_SUBMITLEVELNODE: {
                        System.out.println("Incoming: " + requestRaw.getMessage());
                        SocketRequest_SubmitLevelNode result = ((SocketRequest_SubmitLevelNode) requestRaw);
                        System.out.println(result.levelNode.getValue());
                    }
                }
            }
        };
    }

    public void init() {
        requestHandleThread.start();
    }
}
