package Socket;

import bus.GameServerBUS;
import dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameServer extends Server {
    private GameServerBUS gameServerBUS;
    private ArrayList<GameRoom_Server> gameRooms;

    public GameServer(int port) {
        super(port);
        this.gameRooms = new ArrayList<GameRoom_Server>();
        this.gameServerBUS = new GameServerBUS(this);
    }

    // Properties

    public GameServerBUS getGameServerBUS() {
        return gameServerBUS;
    }

    public ArrayList<GameRoom_Server> getGameRooms() {
        return gameRooms;
    }
}
