package bus;

import Socket.GameClient;
import Socket.Response.SocketResponse_GameRoomProps;
import Socket.Response.SocketResponse_InitGame;
import Socket.Response.SocketResponse_PlayerJoinRoom;
import dto.*;

public class GameRoomBUS {
    GameRoom gameRoom; // PARENT

    public GameRoomBUS(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    /**
     * Phải là static vì lúc bấy giờ GameRoom chưa được khởi tạo
     * @param response SocketResponse nhận từ ClientSocketProcess
     * @param gameClient Nhận gameClient để có khả năng getGameRoom, setGameRoom. GameClient một lúc chỉ có thể có một GameRoom
     */
    public static void listen_clientPlayerJoinRoom(SocketResponse_PlayerJoinRoom response, GameClient gameClient) {
        gameClient.setGameRoom(
            new GameRoom_Client(response.gameRoomId, gameClient)
        );
        gameClient.getGameRoom().setClientPlayer(
            response.clientPlayer_MatchPlayer
        );
    }

    public void listen_setGameRoomProps(SocketResponse_GameRoomProps response) {
        ((GameRoom_Client) this.gameRoom).setPlayers(response.players);
        this.gameRoom.setMatchConfig(response.matchConfig);
        this.gameRoom.setStatus(response.status);
    }

    public void listen_startGame(SocketResponse_InitGame response) {
        MatchPlayer clientPlayer = ((GameRoom_Client) gameRoom).getClientPlayer();

        Game_Client game = new Game_Client(response.game, this.getGameRoom().getClient(), clientPlayer);
        this.gameRoom.setGame(game);
        game.getGameBUS().initGame();

        ViewBUS.gotoGameView(game.getGameBUS());
    }

    // Privates

    private Game_Client getGame() {
        return (Game_Client) this.gameRoom.getGame();
    }

    private GameRoom_Client getGameRoom() {
        return ((GameRoom_Client) this.gameRoom);
    }
}
