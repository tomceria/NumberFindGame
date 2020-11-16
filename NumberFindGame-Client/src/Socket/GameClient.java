package Socket;

import bus.GameClientBUS;
import dto.GameRoom_Client;

import java.io.IOException;

public class GameClient extends Client {
    private GameClientBUS gameClientBUS;
    private GameRoom_Client gameRoom;

    public GameClient() {
        this.gameClientBUS = new GameClientBUS(this);
    }

    // Properties


    public GameClientBUS getGameClientBUS() {
        return gameClientBUS;
    }

    public GameRoom_Client getGameRoom() {
        return gameRoom;
    }
    public void setGameRoom(GameRoom_Client gameRoom) {
        this.gameRoom = gameRoom;
    }
}
