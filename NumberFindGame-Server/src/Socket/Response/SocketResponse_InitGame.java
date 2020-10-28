package Socket.Response;

import dto.Game;

public class SocketResponse_InitGame extends SocketResponse {
    public Game game;

    public SocketResponse_InitGame(Game game) {
        super(Status.SUCCESS, Action.GAME_INIT, "Game started.");
        this.game = game;
    }
}
