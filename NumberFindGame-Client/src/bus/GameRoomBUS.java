package bus;

import Socket.GameClient;
import Socket.Response.SocketResponse_GameRoomProps;
import Socket.Response.SocketResponse_GameInit;
import Socket.Response.SocketResponse_GameRoomPlayerJoin;
import dto.*;

public class GameRoomBUS {
    GameRoom gameRoom; // PARENT

    public GameRoomBUS(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void listen_setGameRoomProps(SocketResponse_GameRoomProps response) {
        ((GameRoom_Client) this.gameRoom).setPlayers(response.players);
        this.gameRoom.setMatchConfig(response.matchConfig);
        this.gameRoom.setStatus(response.status);
    }

    public void listen_startGame(SocketResponse_GameInit response) {
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
