package dto;

import Socket.ClientHandler;
import Socket.GameServer;
import Socket.Response.SocketResponse;
import bus.GameBUS;
import bus.GameRoomBUS;

import java.util.*;

public class GameRoom_Server extends GameRoom {
    private GameServer server; // PARENT
    private HashMap<UUID, ClientHandler> playerClients;
    protected GameRoomBUS gameRoomBUS;

    public GameRoom_Server(GameServer server) {
        super(
                GameRoomBUS.generateRoomId(server.getGameRooms()),
                "NumberFindGame Game Room"
        );
        this.gameRoomBUS = new GameRoomBUS(this);
        this.server = server;
        this.playerClients = new HashMap<UUID, ClientHandler>();

        this.setMatchConfig(this.gameRoomBUS.getDefaultMatchConfig());
        this.setStatus(GameRoomStatus.OPEN);
        this.setGame(null);  // Chưa bắt đầu game ngay lúc tạo phòng
    }

    // Overrides

    @Override
    public void setMatchConfig(MatchConfig matchConfig) {
        super.setMatchConfig(matchConfig);
        /**
         * HƠI MẠNH TAY... Đá người chơi cuối cùng của phòng cho đến khi vừa đủ chỗ
         */
        while (this.playerClients.size() > this.getMatchConfig().getMaxPlayer()) {
            ClientHandler playerClient = (ClientHandler) this.playerClients.values()
                    .toArray()[this.playerClients.size() - 1];
            this.server.getGameServerBUS().quitGame(
                    playerClient,
                    SocketResponse.Status.SUCCESS,
                    "Room slots have been flushed. You are kicked."
            );
        }
        this.getGameRoomBUS().notifyUpdateGameRoomProps();
    }

    // Properties

    public GameServer getServer() {
        return server;
    }

    public HashMap<UUID, ClientHandler> getPlayerClients() {
        return playerClients;
    }

    public GameRoomBUS getGameRoomBUS() {
        return gameRoomBUS;
    }

}
