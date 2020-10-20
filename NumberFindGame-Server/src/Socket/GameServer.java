package Socket;

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

    public class GameRoom {
        private HashMap<UUID, ClientHandler> playerClients = new HashMap<UUID, ClientHandler>();

        public HashMap<UUID, ClientHandler> getPlayerClients() {
            return playerClients;
        }

        public void joinRoom(ClientHandler playerClient) {
            playerClients.put(playerClient.id, playerClient);
            MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
            System.out.println(String.format("%s joined the room.", matchPlayer.getPlayer().getUsername()));
        }
    }
}
