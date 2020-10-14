package Socket;

import java.io.Serializable;

public class SocketResponse implements Serializable {
    public static enum Status {
        SUCCESS,
        FAILED,
        END
    }

    Status status;
    String message;

    public SocketResponse(Status status) {
        this.status = status;
    }
    public SocketResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }
}
