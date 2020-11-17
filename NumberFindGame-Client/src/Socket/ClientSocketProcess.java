package Socket;

import Socket.Response.*;
import bus.*;
import dto.GameRoom_Client;
import dto.Game_Client;

public class ClientSocketProcess extends Thread {
    Client client;  // PARENT
    boolean isRunning = true;

    public ClientSocketProcess(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (isRunning) {
            SocketResponse resultRaw = client.receiveResponse();
            if (resultRaw == null) {
                continue;
            }

            switch (resultRaw.getAction()) {
                case MSG: {
                    System.out.println(String.format("[Server] : %s", resultRaw.getMessage()));
                    break;
                }
                case GAMEROOM_PLAYERJOIN: {
                    if (((GameClient) this.client).getGameRoom() != null) {
                        System.out.println("CLIENT: You're already in a room.");
                    }

                    ((GameClient) this.client).getGameClientBUS()
                            .listen_clientPlayerJoinRoom(
                                    (SocketResponse_GameRoomPlayerJoin) resultRaw
                            );
                    break;
                }
                case GAMEROOM_PROPS: {
                    ((GameClient) client).getGameRoom().getGameRoomBUS()
                            .listen_setGameRoomProps(
                                    (SocketResponse_GameRoomProps) resultRaw
                            );

                    break;
                }
                case GAME_INIT: {
                    GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();

                    gameRoom.getGameRoomBUS()
                            .listen_startGame(
                                    (SocketResponse_GameInit) resultRaw
                            );

                    break;
                }
                case GAME_PROPS: {
                    GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();
                    ((Game_Client) gameRoom.getGame()).getGameBUS()
                            .listen_GameUpdated(
                                    (SocketResponse_GameProps) resultRaw
                            );
                    break;
                }
                case GAME_END: {
                    GameRoom_Client gameRoom = ((GameClient) client).getGameRoom();
                    ((Game_Client) gameRoom.getGame()).getGameBUS()
                            .listen_GameEnd();
                    break;
                }
                case GAME_RESULT: {
                    SocketResponse_GameResult result = (SocketResponse_GameResult) resultRaw;
                    System.out.println(String.format("%s, %s", result.winner.getUsername(), result.clientPlayerIsWinner));
                    break;
                }
                case NET_CLOSE: {
                    client.close();
                    isRunning = false;
                    break;
                }
            }
        }
    }
}
