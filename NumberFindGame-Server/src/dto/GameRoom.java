package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import bus.GameBUS;
import bus.GameRoomBUS;

import java.util.HashMap;
import java.util.UUID;

public class GameRoom {
    private GameServer server; // PARENT
    private GameRoomBUS gameRoomBUS;
    private GameBUS gameBUS;
    private HashMap<UUID, ClientHandler> playerClients;
    private MatchConfig matchConfig;
    private GameRoomStatus status;

    public static enum GameRoomStatus {
        OPEN,
        LOCKED,
        PLAYING
    }

    public GameRoom(GameServer server) {
        this.server = server;
        this.gameRoomBUS = new GameRoomBUS(this);
        this.playerClients = new HashMap<UUID, ClientHandler>();
        this.matchConfig = gameRoomBUS.getDefaultMatchConfig();
        this.status = GameRoomStatus.OPEN;
    }

    // Properties

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

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }

    public void setMatchConfig(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
    }

    public GameRoomStatus getStatus() {
        return status;
    }

    public void setStatus(GameRoomStatus status) {
        this.status = status;
    }
}
