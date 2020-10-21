package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import bus.GameBUS;
import bus.GameRoomBUS;

import java.util.HashMap;
import java.util.UUID;

public class GameRoom_Server extends GameRoom {
    private GameServer server; // PARENT
    private GameRoomBUS gameRoomBUS;
    private GameBUS gameBUS;
    private HashMap<UUID, ClientHandler> playerClients;

    public GameRoom_Server(GameServer server) {
        super();
        this.server = server;
        this.gameRoomBUS = new GameRoomBUS(this);
        this.playerClients = new HashMap<UUID, ClientHandler>();
        this.matchConfig = gameRoomBUS.getDefaultMatchConfig();
    }


    public GameServer getServer() {
        return server;
    }

    public GameRoomBUS getGameRoomBUS() {
        return gameRoomBUS;
    }

    public GameBUS getGameBUS() {
        return gameBUS;
    }

    public void setGameBUS(GameBUS gameBUS) {
        this.gameBUS = gameBUS;
    }

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }

}
