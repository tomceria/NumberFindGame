package Socket.Request;

public class SocketRequest_LeaderboardAll extends SocketRequest {
    public int page;

    public SocketRequest_LeaderboardAll(int page) {
        super(Action.LEADERBOARD_ALL, "Leaderboard request.");
        this.page = page;
    }
}
