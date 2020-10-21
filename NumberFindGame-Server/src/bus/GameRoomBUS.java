package bus;

import Socket.ClientHandler;
import Socket.ClientManager;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_GameRoomProps;
import dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static dto.GameRoomStatus.*;

public class GameRoomBUS {
    private GameRoom_Server gameRoom; // PARENT

    public GameRoomBUS(GameRoom_Server gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void notifyUpdateGameRoomProps() {
        ArrayList<MatchPlayer> matchPlayers = convertClientHandlersToMatchPlayers(gameRoom.getPlayerClients(), false);
        SocketResponse response = new SocketResponse_GameRoomProps(matchPlayers, gameRoom.getMatchConfig(), gameRoom.getStatus());
        broadcastResponseToRoom(response);
    }

    public void reloadMatchConfig() {  // Hàm này cần thiết vì GameServer chỉ có 1 phòng duy nhất và Client ko dc đổi Config
        this.gameRoom.setMatchConfig(getDefaultMatchConfig());
    }

    public void joinRoom(ClientHandler playerClient) {
        if (this.gameRoom.getStatus() != OPEN) {
            // TODO: throw exception
            return;
        }

        this.gameRoom.getPlayerClients().put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients
        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        System.out.println(String.format("%s joined the room.", matchPlayer.getPlayer().getUsername()));

        this.notifyUpdateGameRoomProps();

        // TODO: Change startGame condition
        if (this.gameRoom.getPlayerClients().size() == this.gameRoom.getMatchConfig().getMaxPlayer()) {
            this.startGame();
        }
    }

    public void startGame() {
        MatchConfig matchConfig = this.gameRoom.getMatchConfig();
        ArrayList<MatchPlayer> matchPlayers = convertClientHandlersToMatchPlayers(this.gameRoom.getPlayerClients(), true);

        this.gameRoom.setStatus(PLAYING);
        this.gameRoom.setGameBUS(new GameBUS(matchConfig, matchPlayers));
    }

    public MatchConfig getDefaultMatchConfig() {    // FUTURE-PROOF, sau này có thể cấu hình cho Player thay đổi Config
        // TODO: Read from config.json
        MatchConfig matchConfig = new MatchConfig();
        matchConfig.setNumberQty(100);
        matchConfig.setTime(180000);
        matchConfig.setMaxPlayer(4);
        return matchConfig;
    }

    private ArrayList<MatchPlayer> convertClientHandlersToMatchPlayers(HashMap<UUID, ClientHandler> playerClients, boolean willIncludeMPS) {
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();
        for (ClientHandler playerClient : playerClients.values()) {
            MatchPlayer matchPlayerOG = ((MatchPlayer) playerClient.getClientIdentifier());
            if (willIncludeMPS) {
                matchPlayers.add(matchPlayerOG);
            } else { // Để tránh lỗi Serialize cả MatchPlayer_Server lúc truyền đi, phải tạo mới (clone) MatchPlayer
                MatchPlayer matchPlayer = new MatchPlayer(matchPlayerOG);
                matchPlayers.add(matchPlayer);
            }
        }

        return matchPlayers;
    }

    private void broadcastResponseToRoom(SocketResponse response) {
        ClientManager clientManager = gameRoom.getServer().getClientManager();
        clientManager.sendResponseToBulkClients(gameRoom.getPlayerClients(), response);
    }
}
