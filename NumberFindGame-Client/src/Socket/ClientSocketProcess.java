package Socket;

import Socket.Response.*;
import bus.GameRoomBUS;

public class ClientSocketProcess extends Thread {
    Client client;  // PARENT
    boolean isRunning = true;

    public ClientSocketProcess(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while(isRunning) {
            SocketResponse resultRaw = client.receiveResponse();
            if (resultRaw == null) {
                continue;
            }

            System.out.println("SERVER: " + resultRaw.getMessage()); // TODO: Placeholder
            switch (resultRaw.getAction()) {
                case MSG: {
                    System.out.println(String.format("[Server] : %s", resultRaw.getMessage()));
                    break;
                }
                case GAMEROOM_PLAYERJOIN: {
                    if (((GameClient) client).getGameRoom() != null) {
                        System.out.println("CLIENT: You're already in a room.");
                    }

                    GameRoomBUS.clientPlayerJoinRoom(
                        (SocketResponse_PlayerJoinRoom) resultRaw,
                        (GameClient) client
                    );
                    break;
                }
                case GAMEROOM_PROPS: {
                    ((GameClient) client).getGameRoom().getGameRoomBUS()
                        .setGameRoomProps(
                            (SocketResponse_GameRoomProps) resultRaw
                        );
                    break;
                }
                case GAME_INIT: {
                    System.out.println("CLIENT: Game is started.");
                    System.out.println("time: " + ((SocketResponse_InitGame) resultRaw).game.getStartTime().toString());
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
