package Socket.Request;

public class SocketRequest_GameRoomStartGame extends SocketRequest {
    public SocketRequest_GameRoomStartGame() {
        super(Action.GAMEROOM_STARTGAME, "Start game request.");
    }
}
