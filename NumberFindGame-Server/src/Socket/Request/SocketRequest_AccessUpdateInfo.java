package Socket.Request;

import java.util.Date;

public class SocketRequest_AccessUpdateInfo extends SocketRequest {
	public String username;
	public String password;
	public String email;
	public String firstName;
	public String lastName;
	public String gender;
	public Date birthday;

	public SocketRequest_AccessUpdateInfo(String username, String email, String firstName, String lastName, String gender, Date birthday) {
		super(Action.PLAYER_UPDATEINFO, "Update info request.");
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthday = birthday;
	}
}
