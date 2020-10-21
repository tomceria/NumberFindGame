package bus;

import Socket.ClientHandler;
import Socket.ClientManager;
import Socket.Response.SocketResponse;
import dto.GameRoom;
import dto.MatchConfig;
import dto.MatchPlayer;

import java.util.HashMap;
import java.util.UUID;

public class GameRoomBUS {
    private GameRoom gameRoom; // PARENT

    public GameRoomBUS(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void reloadMatchConfig() {  // Hàm này cần thiết vì GameServer chỉ có 1 phòng duy nhất và Client ko dc đổi Config
        this.gameRoom.setMatchConfig(getDefaultMatchConfig());
    }

    public void joinRoom(ClientHandler playerClient) {
        if (this.gameRoom.getStatus() != GameRoom.GameRoomStatus.OPEN) {
            // TODO: throw exception
            return;
        }

        this.gameRoom.getPlayerClients().put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients
        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        System.out.println(String.format("%s joined the room.", matchPlayer.getPlayer().getUsername()));

        // TODO: Change startGame condition
        if (this.gameRoom.getPlayerClients().size() == this.gameRoom.getMatchConfig().getMaxPlayer()) {
            this.startGame();
        }
    }

    public void startGame() {
        MatchConfig matchConfig = this.gameRoom.getMatchConfig();
        this.gameRoom.setStatus(GameRoom.GameRoomStatus.PLAYING);
        this.gameRoom.setGameBUS(new GameBUS(matchConfig));
    }

    public MatchConfig getDefaultMatchConfig() {    // FUTURE-PROOF, sau này có thể cấu hình cho Player thay đổi Config
        // TODO: Read from config.json
        MatchConfig matchConfig = new MatchConfig();
        matchConfig.setNumberQty(100);
        matchConfig.setTime(180000);
        matchConfig.setMaxPlayer(4);
        return matchConfig;
    }

    public void broadcastResponseToRoom(SocketResponse response) {
        ClientManager clientManager = gameRoom.getServer().getClientManager();
        clientManager.sendResponseToBulkClients(gameRoom.getPlayerClients(), response);
    }
}
