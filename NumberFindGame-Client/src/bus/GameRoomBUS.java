package bus;

import Socket.GameClient;
import Socket.Response.SocketResponse_GameRoomProps;
import Socket.Response.SocketResponse_PlayerJoinRoom;
import dto.GameRoom;
import dto.GameRoom_Client;

public class GameRoomBUS {
    GameRoom gameRoom;

    public GameRoomBUS(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    /**
     * Phải là static vì lúc bấy giờ GameRoom chưa được khởi tạo
     * @param response SocketResponse nhận từ ClientSocketProcess
     * @param gameClient Nhận gameClient để có khả năng getGameRoom, setGameRoom. GameClient một lúc chỉ có thể có một GameRoom
     */
    public static void clientPlayerJoinRoom(SocketResponse_PlayerJoinRoom response, GameClient gameClient) {
        gameClient.setGameRoom(
            new GameRoom_Client(response.gameRoomId)
        );
        gameClient.getGameRoom().setClientPlayer(
            response.clientPlayer_MatchPlayer
        );
    }

    public void setGameRoomProps(SocketResponse_GameRoomProps response) {
        ((GameRoom_Client) gameRoom).setPlayers(response.players);
        gameRoom.setMatchConfig(response.matchConfig);
        gameRoom.setStatus(response.status);
    }
}
