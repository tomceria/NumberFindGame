package bus;

import Socket.ClientHandler;
import Socket.ClientManager;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_GameRoomProps;
import dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static Socket.Response.SocketResponse.Action.*;
import static Socket.Response.SocketResponse.Status.*;
import static dto.GameRoomStatus.*;

public class GameRoomBUS {
    private GameRoom_Server gameRoom; // PARENT

    public GameRoomBUS(GameRoom_Server gameRoom) {
        this.gameRoom = gameRoom;
    }

    public void notifyUpdateGameRoomProps(ArrayList<MatchPlayer> matchPlayers, MatchConfig matchConfig, GameRoomStatus status) {
        if (matchPlayers == null) {
            matchPlayers = convertClientHandlersToMatchPlayers(gameRoom.getPlayerClients(), false);
        }
        if (matchConfig == null) {
            matchConfig = gameRoom.getMatchConfig();
        }
        if (status == null) {
            status = gameRoom.getStatus();
        }
        SocketResponse response = new SocketResponse_GameRoomProps(matchPlayers, matchConfig, status);
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

        /**
         * 1. Thông báo về có người chơi mới vào phòng cho cả phòng
         */
        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        broadcastResponseToRoom( new SocketResponse(SUCCESS, MSG, String.format("%s joined the room.", matchPlayer.getPlayer().getUsername())) );

        /**
         * 2. Cho Player mới (playerClient) vào phòng (phía Server)
         */
        this.gameRoom.getPlayerClients().put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients


        /**
         * 3. Thông báo về cập nhật Game state cho toàn phòng
         */
        ArrayList<MatchPlayer> matchPlayers = convertClientHandlersToMatchPlayers(gameRoom.getPlayerClients(), false);
        this.notifyUpdateGameRoomProps(matchPlayers, null, null);

        /**
         * 4. Cho Player mới (playerClient) vào phòng (phía Client)
         */
//        sendResponseToPlayer(
//                new SocketResponse_PlayerJoinRoom(matchPlayers.indexOf())
//        );

        // TODO: Change startGame condition
        if (this.gameRoom.getPlayerClients().size() == this.gameRoom.getMatchConfig().getMaxPlayer()) {
            this.startGame();
        }
    }

    public void leaveRoom(ClientHandler playerClient) {
        this.gameRoom.getPlayerClients().remove(playerClient.getId());

        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        broadcastResponseToRoom( new SocketResponse(SUCCESS, MSG, String.format("%s left the room.", matchPlayer.getPlayer().getUsername())) );
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

    // Privates

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

    private void sendResponseToPlayer(SocketResponse response, UUID clientHandlerId) {
        ClientManager clientManager = gameRoom.getServer().getClientManager();
        clientManager.sendResponseToClient(clientHandlerId, response);
    }

    private void broadcastResponseToRoom(SocketResponse response) {
        ClientManager clientManager = gameRoom.getServer().getClientManager();
        clientManager.sendResponseToBulkClients(gameRoom.getPlayerClients(), response);
    }
}
