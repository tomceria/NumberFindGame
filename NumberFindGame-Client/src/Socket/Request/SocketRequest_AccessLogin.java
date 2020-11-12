package Socket.Request;

public class SocketRequest_AccessLogin extends SocketRequest {
    public String username;
    public String password;

    public SocketRequest_AccessLogin(String username, String password) {
        super(SocketRequest.Action.ACCESS_LOGIN, "Login request.");
        this.username = username;
        this.password = password;
    }
}
