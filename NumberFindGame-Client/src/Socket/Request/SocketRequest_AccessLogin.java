package Socket.Request;

import Socket.Helper.EncryptionHelper;

import java.security.*;

public class SocketRequest_AccessLogin extends SocketRequest {
    public String username;
    public String password;
    public PublicKey publicKey;

    public SocketRequest_AccessLogin(String username, String password) {
        super(SocketRequest.Action.ACCESS_LOGIN, "Login request.");
        this.username = username;
        this.password = password;
        this.publicKey = EncryptionHelper.ClientPublicKey;
    }
}
