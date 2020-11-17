package bus;

import Socket.ClientHandler;
import Socket.GameServer;
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
        this.joinRoom(playerClient, defaultGameRoomId); // TODO: Phòng đầy. đá Client ra thì Client đi đâu???
    }

    public void joinRoom(ClientHandler playerClient, int gameRoomId) {
        GameRoom_Server gameRoom =  this.gameServer.getGameRooms()
                .stream().filter(gR -> gR.getId() == gameRoomId)
                .collect(Collectors.toList())
                .get(0);
        gameRoom.getGameRoomBUS().joinRoom(playerClient);
    }
}
