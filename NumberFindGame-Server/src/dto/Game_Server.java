package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import bus.GameBUS;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Game_Server extends Game {
    private GameServer server;
    private HashMap<UUID, ClientHandler> playerClients;
    private GameBUS gameBUS;

    public Game_Server(GameServer server, HashMap<UUID, ClientHandler> playerClients, MatchConfig matchConfig, ArrayList<MatchPlayer> players, GameRoomInfo gameRoomInfo) {
        super(matchConfig, players, gameRoomInfo);
        this.gameBUS = new GameBUS(this);

        this.server = server;
        this.playerClients = playerClients;
    }

    // Additional functions

    public void setCurrentLevelAndResetTimer(int currentLevelValue) {
        this.currentLevel = new CurrentLevel();
        this.currentLevel.setValue(currentLevelValue);
        this.currentLevel.setTimeStart(LocalTime.now());                                  // Set will also restart timer
    }

    // Properties

    public GameServer getServer() {
        return server;
    }

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }

    public GameBUS getGameBUS() {
        return gameBUS;
    }
}
