package dto;

import Socket.ClientHandler;
import bus.GameBUS;

import java.util.HashMap;
import java.util.UUID;

public class GameRoom {
    private HashMap<UUID, ClientHandler> playerClients;
    private MatchConfig matchConfig;
    private GameBUS gameBUS;

    public GameRoom() {
        this.playerClients = new HashMap<UUID, ClientHandler>();
        this.matchConfig = getDefaultMatchConfig();
    }

    public void reloadMatchConfig() {  // Hàm này cần thiết vì GameServer chỉ có 1 phòng duy nhất và Client ko dc đổi Config
        this.matchConfig = getDefaultMatchConfig();
    }

    public void joinRoom(ClientHandler playerClient) {
        playerClients.put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients
        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        System.out.println(String.format("%s joined the room.", matchPlayer.getPlayer().getUsername()));
    }

    public void startGame() {
        this.gameBUS = new GameBUS(matchConfig);
    }

    private MatchConfig getDefaultMatchConfig() {    // FUTURE-PROOF, sau này có thể cấu hình cho Player thay đổi Config
        // TODO: Read from config.json
        MatchConfig matchConfig = new MatchConfig();
        matchConfig.setNumberQty(100);
        matchConfig.setTime(180000);
        matchConfig.setMaxPlayer(4);
        return matchConfig;
    }

    // Properties

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }
}
