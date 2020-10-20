package Socket;

import dto.GameRoom;
import dto.MatchPlayer;
import dto.PlayerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameServer extends Server {
    private ArrayList<GameRoom> gameRooms;

    public GameServer(int port) {
        super(port);
        // TODO: Game business Logic: Create a room upon server starting
        gameRooms = new ArrayList<GameRoom>() {{
            add(new GameRoom());
        }};
    }

    public ArrayList<GameRoom> getGameRooms() {
        return gameRooms;
    }

    public void joinGame(ClientHandler playerClient) {
        gameRooms.get(0).joinRoom(playerClient);
    }
}
