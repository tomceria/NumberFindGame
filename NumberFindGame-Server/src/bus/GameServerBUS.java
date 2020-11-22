package bus;

import Socket.ClientHandler;
import Socket.GameServer;
import Socket.Response.SocketResponse;
import dto.GameRoom_Server;
import dto.MatchPlayer_Server;

import java.util.stream.Collectors;

public class GameServerBUS {
    GameServer gameServer;  // PARENT

    public GameServerBUS(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    // Functions

    public void joinGame(ClientHandler playerClient) {
        /**
         * Nếu đúng là sẽ đưa Player vào Lobby. Nhưng vì giới hạn về thời gian phát triển nên ko có hệ thống Lobby
         * Thay vào đó, khi đăng nhập Player được đưa thẳng vào 1 phòng chơi duy nhất của Server
         */
        ((MatchPlayer_Server) playerClient.getClientIdentifier()).setServerBUS(this);

        if (this.gameServer.getGameRooms().size() < 1) {
            this.gameServer.getGameRooms().add(new GameRoom_Server(this.gameServer));
        }
        int defaultGameRoomId = this.gameServer.getGameRooms().get(0).getId();
        try {
            this.joinRoom(playerClient, defaultGameRoomId);
        } catch (RuntimeException e) {
            this.quitGame(playerClient, SocketResponse.Status.FAILED, e.getMessage());
        }
    }

    public void joinRoom(ClientHandler playerClient, int gameRoomId) {
        GameRoom_Server gameRoom =  this.gameServer.getGameRooms()
                .stream().filter(gR -> gR.getId() == gameRoomId)
                .collect(Collectors.toList())
                .get(0);
        gameRoom.getGameRoomBUS().joinRoom(playerClient);
    }

    public void quitGame(ClientHandler playerClient, SocketResponse.Status status, String message) {
        /**
         * Thông báo cho Client rằng Server sẽ thực hiện đóng kết nối giữa Client và Server
         * Cách làm này tránh cho Client bị kẹt ở readObject()
         */
        this.gameServer.getClientManager().sendResponseToClient(
                playerClient.getId(),
                new SocketResponse(status, SocketResponse.Action.NET_CLOSE, message)
        );

        /**
         * Xoá playerClient khỏi Server
         */
        this.gameServer.getClientManager().disconnectClient(playerClient.getId());
    }
}
