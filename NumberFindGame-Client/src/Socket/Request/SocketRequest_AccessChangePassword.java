package Socket.Request;

public class SocketRequest_AccessChangePassword extends SocketRequest {
	public String username;
	public String password;

	public SocketRequest_AccessChangePassword(String username, String password) {
		super(Action.PLAYER_CHANGEPASSWORD, "Change password request.");
		this.username = username;
		this.password = password;
	}
}
