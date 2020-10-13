package Socket;

import java.io.Serializable;

public abstract class SocketRequest implements Serializable {
    public static enum Action {
        LOGIN,
        DISCONNECT
    }

    Action action;

    public SocketRequest(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
