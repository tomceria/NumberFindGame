package Socket;

public class LogInRequest extends SocketRequest {
    String username;
    String password;

    public LogInRequest(String username, String password) {
        super(SocketRequest.Action.LOGIN);
        this.username = username;
        this.password = password;
    }
}
