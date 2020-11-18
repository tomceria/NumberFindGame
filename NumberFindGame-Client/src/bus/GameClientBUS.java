package bus;

import Socket.GameClient;
import Socket.Response.SocketResponse_GameRoomPlayerJoin;
import dto.GameRoom_Client;

public class GameClientBUS {
    GameClient gameClient; // PARENT

    public GameClientBUS(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    // Functions

    /**
     * gameClient: Nhận gameClient để có khả năng getGameRoom, setGameRoom. GameClient một lúc chỉ có thể có một GameRoom
     * @param response SocketResponse nhận từ ClientSocketProcess
     */
    public void listen_clientPlayerJoinRoom(SocketResponse_GameRoomPlayerJoin response) {
        gameClient.setGameRoom(
                new GameRoom_Client(response.gameRoomId, response.gameRoomName, this.gameClient)
        );
        gameClient.getGameRoom().setClientPlayer(
                response.clientPlayer_MatchPlayer
        );

        ViewBUS.gotoGameRoomView(gameClient.getGameRoom().getGameRoomBUS());
    }
}
