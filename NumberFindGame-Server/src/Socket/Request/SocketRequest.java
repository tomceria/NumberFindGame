package Socket.Request;

import java.io.Serializable;

public class SocketRequest implements Serializable {
    public static enum Action {
        MSG,
        ACCESS_LOGIN,
        ACCESS_REGISTER,
        PLAYER_UPDATEINFO,
        PLAYER_CHANGEPASSWORD,
        DISCONNECT,
        GAMEROOM_STARTGAME,
        GAMEROOM_QUIT,
        GAME_SUBMITLEVELNODE,
        GAME_QUIT,
        LEADERBOARD_ALL,
        LEADERBOARD_USER
    }

    Action action;
    String message;

    public SocketRequest(Action action) {
        this.action = action;
    }
    public SocketRequest(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public Action getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }
}
