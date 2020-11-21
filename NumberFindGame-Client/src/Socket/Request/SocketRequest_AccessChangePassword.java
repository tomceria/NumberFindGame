package Socket.Request;

public class SocketRequest_AccessChangePassword extends SocketRequest {
	public String username;
	public String password;
	public String email;
	public String firstName;
	public String lastName;

	public SocketRequest_AccessChangePassword(String username, String password) {
		super(Action.ACCESS_CHANGEPASSWORD, "Change password request.");
		this.username = username;
		this.password = password;
	}
}
