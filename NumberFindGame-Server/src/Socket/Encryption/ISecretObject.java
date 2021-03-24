package Socket.Encryption;

import Socket.Request.SocketRequest;
import Socket.Response.SocketResponse;

import java.io.*;

public interface ISecretObject extends Serializable {
    SocketResponse getSecretResponse();
    SocketRequest getSecretRequest();
}