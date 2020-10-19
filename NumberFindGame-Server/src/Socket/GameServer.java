package Socket;

public class GameServer extends Server {
    public GameRoom gameRoom;

    public GameServer(int port) {
        super(port);
        gameRoom = new GameRoom();
    }
}
