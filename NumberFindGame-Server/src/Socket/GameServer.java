package Socket;

import dto.PlayerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameServer extends Server {
    private ArrayList<GameRoom> gameRooms;

    public GameServer(int port) {
        super(port);
        gameRooms = new ArrayList<GameRoom>() {{
            add(new GameRoom());
        }};
    }

    public ArrayList<GameRoom> getGameRooms() {
        return gameRooms;
    }

    public class GameRoom {
        private HashMap<UUID, ClientHandler> playerClients = new HashMap<UUID, ClientHandler>();

        public HashMap<UUID, ClientHandler> getPlayerClients() {
            return playerClients;
        }

        public void joinRoom(ClientHandler playerClient) {
            playerClients.put(playerClient.id, playerClient);
            PlayerDTO player = (PlayerDTO) playerClient.getClientIdentifier();
            System.out.println(String.format("%s joined the room.", player.getUsername()));
        }
    }
}
