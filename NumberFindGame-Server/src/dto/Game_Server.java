package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import bus.GameBUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Game_Server extends Game {
    private GameServer server;
    private HashMap<UUID, ClientHandler> playerClients;
    private GameBUS gameBUS;

    public Game_Server(GameServer server, HashMap<UUID, ClientHandler> playerClients, MatchConfig matchConfig, ArrayList<MatchPlayer> players) {
        super(matchConfig, players);
        this.gameBUS = new GameBUS(this);

        this.server = server;
        this.playerClients = playerClients;
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
