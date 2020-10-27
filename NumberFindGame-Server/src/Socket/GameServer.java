package Socket;

import dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameServer extends Server {
    private ArrayList<GameRoom_Server> gameRooms;
    private MatchConfig matchConfig;

    public GameServer(int port) {
        super(port);
        // TODO: Game business Logic: Create a room upon server starting
        gameRooms = new ArrayList<GameRoom_Server>();
        gameRooms.add(new GameRoom_Server(GameServer.this));
    }

    public ArrayList<GameRoom_Server> getGameRooms() {
        return gameRooms;
    }

    public void joinGame(ClientHandler playerClient) {
        // Nếu đúng là sẽ đưa Player vào Lobby. Nhưng vì giới hạn về thời gian phát triển nên ko có hệ thống Lobby
        // Thay vào đó, khi đăng nhập Player được đưa thẳng vào 1 phòng chơi duy nhất của Server
        GameRoom_Server defaultGameRoom = gameRooms.get(0);
        defaultGameRoom.getGameRoomBUS().joinRoom(playerClient);
    }
}
