package Socket.Encryption;

import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;

public class SecretObjectImpl implements ISecretObject {
    private SocketResponse secretResponse;
    private SocketRequest secretRequest;

    public SecretObjectImpl(SocketResponse secretResponse) {
        this.secretResponse = secretResponse;
    }

    public SecretObjectImpl(SocketRequest secretRequest) {
        this.secretRequest = secretRequest;
    }

    public SocketResponse getSecretResponse() {
        return secretResponse;
    }

    public SocketRequest getSecretRequest() {
        return secretRequest;
    }
}
