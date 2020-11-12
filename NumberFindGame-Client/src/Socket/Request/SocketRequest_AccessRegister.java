package Socket.Request;

public class SocketRequest_AccessRegister extends SocketRequest {
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;

    public SocketRequest_AccessRegister(String username, String password, String email, String firstName, String lastName) {
        super(Action.ACCESS_REGISTER, "Register request.");
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
