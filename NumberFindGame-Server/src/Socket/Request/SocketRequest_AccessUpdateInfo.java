package Socket.Request;

public class SocketRequest_AccessUpdateInfo extends SocketRequest {
	public String username;
	public String password;
	public String email;
	public String firstName;
	public String lastName;

	public SocketRequest_AccessUpdateInfo(String username, String email, String firstName, String lastName) {
		super(Action.PLAYER_UPDATEINFO, "Update info request.");
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
