package Socket.Request;

public class SocketRequest_LeaderboardUser extends SocketRequest {
    public String username;

    public SocketRequest_LeaderboardUser(String username) {
        super(Action.LEADERBOARD_USER, "User's ranking request.");
        this.username = username;
    }
}
