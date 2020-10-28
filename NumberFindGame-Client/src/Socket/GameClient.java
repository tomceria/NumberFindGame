package Socket;

import dto.GameRoom_Client;

import java.io.IOException;

public class GameClient extends Client {
    private GameRoom_Client gameRoom;

    public GameRoom_Client getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom_Client gameRoom) {
        this.gameRoom = gameRoom;
    }
}
