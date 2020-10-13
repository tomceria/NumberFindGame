package Socket;

import java.io.Serializable;

public class SocketRequest implements Serializable {
    String action;

    public SocketRequest(String action) {
        this.action = action;
    }
}
