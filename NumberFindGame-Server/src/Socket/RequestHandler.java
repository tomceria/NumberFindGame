package Socket;

import Socket.Request.SocketRequest;
import Socket.Request.SocketRequest_AccessLogin;
import Socket.Request.SocketRequest_AccessRegister;
import Socket.Request.SocketRequest_GameSubmitLevelNode;
import Socket.Response.SocketResponse;
import bus.PlayerBUS;
import dto.MatchPlayer_Server;
import dto.PlayerDTO;

public class RequestHandler {
    Thread requestHandleThread;
    ClientHandler clientHandler;
    SocketRequest requestRaw;

    public RequestHandler(SocketRequest requestRaw, IClientIdentifier clientIdentifier, ClientHandler clientHandler) {
        this.requestRaw = requestRaw;
        this.clientHandler = clientHandler;
        this.requestHandleThread = new Thread() {
            @Override
            public void run() {
                ClientHandler thisClientHandler = RequestHandler.this.clientHandler;

                if (thisClientHandler.isLoggedIn == false) {
                    switch (requestRaw.getAction()) {
                        case ACCESS_LOGIN: {
                            if (performValidateClient(requestRaw)) {
                                thisClientHandler.isLoggedIn = true;
                                thisClientHandler.sendResponse(new SocketResponse(
                                        SocketResponse.Status.SUCCESS,
                                        SocketResponse.Action.MSG,
                                        "Logged in.")
                                );
                                onSuccessConnection();
                            } else {
                                thisClientHandler.sendResponse(new SocketResponse(
                                        SocketResponse.Status.FAILED,
                                        SocketResponse.Action.MSG,
                                        "Invalid username or password.")
                                );
                                thisClientHandler.isRunning = false;
                            }
                            break;
                        }
                        case ACCESS_REGISTER: {
                            SocketRequest_AccessRegister request = ((SocketRequest_AccessRegister) requestRaw);
                            System.out.println(request.getMessage());
                        }
                        default: {
                            thisClientHandler.sendResponse(new SocketResponse(SocketResponse.Status.FAILED, SocketResponse.Action.MSG, "Invalid access request."));
                            thisClientHandler.isRunning = false;  // Yêu cầu ĐẦU TIÊN không hợp lệ => Thoát khỏi vòng lặp => Kết thúc Thread => Disconnect
                            break;
                        }
                    }
                } else if (thisClientHandler.isLoggedIn &&
                        thisClientHandler.clientIdentifier != null) {
                    switch (requestRaw.getAction()) {
                        case MSG: {
                            System.out.println("Received message: " + requestRaw.getMessage());
                            break;
                        }
                        case GAME_SUBMITLEVELNODE: {
                            SocketRequest_GameSubmitLevelNode request = ((SocketRequest_GameSubmitLevelNode) requestRaw);
                            MatchPlayer_Server matchPlayer = (MatchPlayer_Server) clientIdentifier;

                            matchPlayer.getGameBUS().req_sendLevelNodeForValidation(
                                    request.levelNode, matchPlayer
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
        SocketRequest_AccessLogin request = (SocketRequest_AccessLogin) requestRaw;

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
