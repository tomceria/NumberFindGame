package Socket;

import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_PlayerJoinRoom;
import bus.GameRoomBUS;
import dto.GameRoom_Client;

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

            switch (resultRaw.getAction()) {
                case MSG: {
                    System.out.println(String.format("[Server] : %s", resultRaw.getMessage()));
                    break;
                }
                case UPDATE_GAMEROOM: {
                    System.out.println("SERVER: " + resultRaw.getMessage()); // TODO: Placeholder
                    break;
                }
                case UPDATE_PLAYERJOINROOM: {
                    if (((GameClient) client).getGameRoom() != null) {
                        System.out.println("CLIENT: You're already in a room.");
                    }
                    System.out.println("SERVER: " + resultRaw.getMessage()); // TODO: Placeholder

                    GameRoomBUS.clientPlayerJoinRoom(
                            (SocketResponse_PlayerJoinRoom) resultRaw,
                            ((GameClient) client)
                    );
                    break;
                }
                case NET_CLOSE: {
                    System.out.println(resultRaw.getMessage());
                    client.close();
                    isRunning = false;
                    break;
                }
            }
        }
    }
}
