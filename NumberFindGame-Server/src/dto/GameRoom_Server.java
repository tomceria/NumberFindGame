package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import bus.GameBUS;
import bus.GameRoomBUS;

import java.util.HashMap;
import java.util.UUID;

public class GameRoom_Server extends GameRoom {
    private GameServer server; // PARENT
    private HashMap<UUID, ClientHandler> playerClients;

    public GameRoom_Server(GameServer server) {
        super();
        this.gameRoomBUS = new GameRoomBUS(this);
        this.server = server;
        this.playerClients = new HashMap<UUID, ClientHandler>();
    }

    public GameServer getServer() {
        return server;
    }

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }
}
