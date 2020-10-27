package Socket;

import dto.GameRoom_Client;

import java.io.IOException;

public class GameClient extends Client {
    private GameRoom_Client gameRoom;

    public GameClient(String hostname, int port, String username, String password) throws IOException, ClassNotFoundException {
        super(hostname, port, username, password);
    }

    public GameRoom_Client getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom_Client gameRoom) {
        this.gameRoom = gameRoom;
    }
}
