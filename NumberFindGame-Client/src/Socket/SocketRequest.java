package Socket;

import java.io.Serializable;

public abstract class SocketRequest implements Serializable {
    String action;

    public SocketRequest(String action) {
        this.action = action;
    }
}
