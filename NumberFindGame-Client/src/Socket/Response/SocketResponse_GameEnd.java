package Socket.Response;

public class SocketResponse_GameEnd extends SocketResponse {
    public SocketResponse_GameEnd() {
        super(Status.SUCCESS, Action.GAME_END, "Game ends.");
    }
}
