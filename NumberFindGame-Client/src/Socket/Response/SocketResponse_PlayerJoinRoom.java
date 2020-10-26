package Socket.Response;

public class SocketResponse_PlayerJoinRoom extends SocketResponse {
    public String clientPlayerId_MatchPlayer;

    public SocketResponse_PlayerJoinRoom(String clientPlayerId_MatchPlayer) {
        super(SocketResponse.Status.SUCCESS, Action.UPDATE_PLAYERJOINROOM, "Set Client's MatchPlayer");
        this.clientPlayerId_MatchPlayer = clientPlayerId_MatchPlayer;
        this.status = status;
    }
}
