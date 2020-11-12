package Socket.Request;

public class SocketRequest_Login extends SocketRequest {
    public String username;
    public String password;

    public SocketRequest_Login(String username, String password) {
        super(SocketRequest.Action.ACCESS_LOGIN, "Login request.");
        this.username = username;
        this.password = password;
    }
}
