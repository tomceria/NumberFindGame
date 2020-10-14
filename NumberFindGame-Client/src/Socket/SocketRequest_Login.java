package Socket;

public class SocketRequest_Login extends SocketRequest {
    String username;
    String password;

    public SocketRequest_Login(String username, String password) {
        super(SocketRequest.Action.LOGIN, "Login attempt detected.");
        this.username = username;
        this.password = password;
    }
}
