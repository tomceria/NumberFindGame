package bus;

import Socket.ClientHandler;
import Socket.ClientManager;
import Socket.GameServer;
import Socket.Response.SocketResponse;
import Socket.Response.SocketResponse_GameRoomProps;
import Socket.Response.SocketResponse_InitGame;
import Socket.Response.SocketResponse_PlayerJoinRoom;
import dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import static Socket.Response.SocketResponse.Action.*;
import static Socket.Response.SocketResponse.Status.*;
import static dto.GameRoomStatus.*;

public class GameRoomBUS {
    private GameRoom_Server gameRoom; // PARENT

    public GameRoomBUS(GameRoom_Server gameRoom) {
        this.gameRoom = gameRoom;
        this.gameRoom.setMatchConfig(getDefaultMatchConfig());

        this.gameRoom.setStatus(GameRoomStatus.OPEN);
        this.gameRoom.setGame(null);                                          // Chưa bắt đầu game ngay lúc tạo phòng
    }

    public static int generateRoomId(ArrayList<GameRoom_Server> gameRooms) {
        int i = 1;                                                                              // id phòng bắt đầu từ 1

        if (gameRooms.size() == 0) {                                                     // Chưa có phòng nào => KQ là 1
            return i;
        }

        /**
         * Mục tiêu: Tìm id trống, VD [1, 2, 4, 5] thì kết quả là 3
         */
        Collections.sort(gameRooms, (o1, o2) -> o1.getId() < o2.getId() ? -1 : 1);           // Sắp xếp tăng dần theo id
        for (GameRoom gameRoom : gameRooms) {
            if (gameRoom.getId() == i) {
                i++;
            } else {                                                      // Tìm thấy khoảng trống (chính là i) => Ngưng
                break;
            }
        }

        return i;
    }

    public void notifyUpdateGameRoomProps(ArrayList<MatchPlayer> matchPlayers, MatchConfig matchConfig, GameRoomStatus status) {
        if (matchPlayers == null) {
            matchPlayers = convertClientHandlersToMatchPlayers(this.gameRoom.getPlayerClients(), false);
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
        broadcastResponseToRoom(new SocketResponse(
            SUCCESS,
            MSG,
            String.format("%s joined Room %d.", matchPlayer.getPlayer().getUsername(), gameRoom.getId())
        ));

        /**
         * 2. Cho Player mới (playerClient) vào phòng (phía Server)
         */
        this.gameRoom.getPlayerClients().put(playerClient.getId(), playerClient);  // Copy ClientHandler từ ClientManager.clientConnections sang GameRoom.playerClients

        /**
         * 3. Cho Player mới (playerClient) vào phòng (phía Client)
         */
        sendResponseToPlayer(
            new SocketResponse_PlayerJoinRoom(
                this.gameRoom.getId(),
                new MatchPlayer((MatchPlayer) playerClient.getClientIdentifier())   // clone để không có reference đến MatchPlayerServer
            ),
            playerClient.getId()
        );

        /**
         * 4. Thông báo về cập nhật Game state cho toàn phòng
         * Hành động này phải thực hiện sau bước "2" vì playerClient cần được nhận notifyUpdateGameRoomProps ngay khi vào phòng
         * Vì lúc này getMatchPlayersInRoomToSendResponse() đã chứa playerClient
         */
        this.notifyUpdateGameRoomProps(this.getMatchPlayersInRoomToSendResponse(), null, null);

        /**
         * Gán GameRoomBUS cho ClientIdentifier
         */
        ((MatchPlayer_Server) playerClient.getClientIdentifier()).setGameRoomBUS(this);

        // TODO: Change startGame condition
        if (this.gameRoom.getPlayerClients().size() == this.gameRoom.getMatchConfig().getMaxPlayer()) {
            this.startGame();
        }
    }

    public void leaveRoom(ClientHandler playerClient) {
        this.gameRoom.getPlayerClients().remove(playerClient.getId());                               // Thoát khỏi phòng
        getServer().getClientManager().getClientConnections().remove(playerClient.getId());            // Đá khỏi server

        MatchPlayer matchPlayer = (MatchPlayer) playerClient.getClientIdentifier();
        if (this.gameRoom.getPlayerClients().size() > 0) {
            broadcastResponseToRoom( new SocketResponse(SUCCESS, MSG, String.format("%s left the room.", matchPlayer.getPlayer().getUsername())) );
            this.notifyUpdateGameRoomProps(this.getMatchPlayersInRoomToSendResponse(), null, null);
        }
    }

    public void startGame() {
        /**
         * Thông báo cho mọi người chơi trong phòng rằng Game đang được bắt đầu
         * Sau khi broadcast, GameRoomBUS sẽ bước vào giai đoạn xử lý
         */
        this.broadcastResponseToRoom(
            new SocketResponse(SUCCESS, MSG, "Starting game...")
        );

        /**
         * Lấy MatchConfig và danh sách người chơi từ GameRoom đưa vào Game
         * => Các MatchConfig được chỉnh sửa khi trận đấu đang diễn ra chỉ thay đổi trên GameRoom
         */
        MatchConfig matchConfig = this.gameRoom.getMatchConfig();
        ArrayList<MatchPlayer> matchPlayers = convertClientHandlersToMatchPlayers(this.gameRoom.getPlayerClients(), true);
        this.gameRoom.setStatus(PLAYING);
        this.gameRoom.setGame(
            new Game_Server(
                    this.getServer(),
                    gameRoom.getPlayerClients(),
                    matchConfig,
                    matchPlayers)
        );
        this.getGame().getGameBUS().initGame();

        /**
         * Xử lý hoàn tất, broadcast thông tin về Game vừa được tạo
         */
        Game gameSerializable = new Game(this.getGame(), true);
        this.broadcastResponseToRoom(
            new SocketResponse_InitGame(gameSerializable)
        );

        /**
         * Gán GameBUS cho mọi MatchPlayer_Server (IClientIdentifier) trong phòng
         * => Để tiện "liên lạc" sau này (RequestHandler gọi BUS thông qua MatchPlayer_Server)
         * (BẮT BUỘC phải được thực hiện sau this.gameRoom.setGame())
         */
        for (ClientHandler playerClient : this.gameRoom.getPlayerClients().values()) {
            MatchPlayer_Server matchPlayer = (MatchPlayer_Server) playerClient.getClientIdentifier();
            matchPlayer.setGameBUS(this.getGame().getGameBUS());
        }
    }

    public MatchConfig getDefaultMatchConfig() {    // FUTURE-PROOF, sau này có thể cấu hình cho Player thay đổi Config
        // TODO: Read from config.json
        MatchConfig matchConfig = new MatchConfig();
        matchConfig.setNumberQty(50);
        matchConfig.setTime(180000);
        matchConfig.setMaxPlayer(2);
        return matchConfig;
    }

    // Privates

    private GameServer getServer() {
        return gameRoom.getServer();
    }

    private Game_Server getGame() {
        return ((Game_Server) this.gameRoom.getGame());
    }

    private ArrayList<MatchPlayer> getMatchPlayersInRoomToSendResponse() {
        return convertClientHandlersToMatchPlayers(this.gameRoom.getPlayerClients(), false);
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

    private void sendResponseToPlayer(SocketResponse response, UUID clientHandlerId) {
        ClientManager clientManager = this.getServer().getClientManager();
        clientManager.sendResponseToClient(clientHandlerId, response);
    }

    private void broadcastResponseToRoom(SocketResponse response) {
        ClientManager clientManager = this.getServer().getClientManager();
        clientManager.sendResponseToBulkClients(this.gameRoom.getPlayerClients(), response);
    }
}
