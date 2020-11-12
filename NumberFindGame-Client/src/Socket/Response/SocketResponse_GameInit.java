package Socket.Response;

import dto.Game;

public class SocketResponse_GameInit extends SocketResponse {
    public Game game;

    public SocketResponse_GameInit(Game game) {
        super(Status.SUCCESS, Action.GAME_INIT, "Game started.");
        this.game = game;
    }
}
