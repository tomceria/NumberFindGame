package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_Login;
import Socket.Request.SocketRequest_SubmitLevelNode;
import Socket.Response.SocketResponse;
import bus.PlayerBUS;
import dto.MatchPlayer_Server;
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
                if (RequestHandler.this.clientHandler.isLoggedIn == false) {
                    switch (requestRaw.getAction()) {
                        case ACCESS_LOGIN: {
                            if (requestRaw.getAction().equals(SocketRequest.Action.ACCESS_LOGIN)) {
                                if (performValidateClient(requestRaw)) {
                                    RequestHandler.this.clientHandler.isLoggedIn = true;
                                    RequestHandler.this.clientHandler.sendResponse(new SocketResponse(
                                            SocketResponse.Status.SUCCESS,
                                            SocketResponse.Action.MSG,
                                            "Logged in.")
                                    );
                                    onSuccessConnection();
                                } else {
                                    RequestHandler.this.clientHandler.sendResponse(new SocketResponse(
                                            SocketResponse.Status.FAILED,
                                            SocketResponse.Action.MSG,
                                            "Invalid username or password.")
                                    );
                                    RequestHandler.this.clientHandler.isRunning = false;
                                }
                            } else {
                                RequestHandler.this.clientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MSG, "Invalid access request."));
                                RequestHandler.this.clientHandler.isRunning = false;  // Yêu cầu ĐẦU TIÊN không hợp lệ => Thoát khỏi vòng lặp => Kết thúc Thread => Disconnect
                            }
                            break;
                        }
                    }
                } else if (RequestHandler.this.clientHandler.isLoggedIn &&
                        RequestHandler.this.clientIdentifier != null) {
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
                            break;
                        }
                    }
                }
            }
        };
    }

    public void init() {
        requestHandleThread.start();
    }

    // Privates

    private boolean performValidateClient(SocketRequest requestRaw) {
        boolean isValidated = false;
        SocketRequest_Login request = (SocketRequest_Login) requestRaw;

        // TODO: BUSINESS LOGIC
        PlayerBUS playerBUS = new PlayerBUS();
        if (playerBUS.login(request.username, request.password)) {
            PlayerDTO player = playerBUS.getOneByUsername(request.username);
            this.clientHandler.clientIdentifier = new MatchPlayer_Server(player);
            isValidated = true;
        }

        return isValidated;
    }

    private void onSuccessConnection() {
        ((GameServer) this.clientHandler.clientManager.server)
                .joinGame(this.clientHandler);
    }

}
