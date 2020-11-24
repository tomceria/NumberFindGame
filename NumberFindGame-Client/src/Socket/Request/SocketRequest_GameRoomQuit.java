package Socket.Request;

public class SocketRequest_GameRoomQuit extends SocketRequest {
    public SocketRequest_GameRoomQuit() {
        super(Action.GAMEROOM_QUIT, "Quit game room request.");
    }
}
