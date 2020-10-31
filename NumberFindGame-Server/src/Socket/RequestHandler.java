package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_SubmitLevelNode;
import dto.MatchPlayer_Server;

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
                    SocketRequest_SubmitLevelNode result = ((SocketRequest_SubmitLevelNode) requestRaw);
                    MatchPlayer_Server matchPlayer = (MatchPlayer_Server) clientIdentifier;

                    matchPlayer.getGameBUS().req_sendLevelNodeForValidation(
                            result.levelNode, matchPlayer
                    );
                }
            }
            }
        };
    }

    public void init() {
        requestHandleThread.start();
    }
}
