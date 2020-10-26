package Socket.Response;

public class SocketResponse_SetClientPlayer extends SocketResponse {
    public String clientPlayerId_MatchPlayer;

    public SocketResponse_SetClientPlayer(String clientPlayerId_MatchPlayer) {
        super(SocketResponse.Status.SUCCESS, Action.UPDATE_CLIENTPLAYER, "Set Client's MatchPlayer");
        this.clientPlayerId_MatchPlayer = clientPlayerId_MatchPlayer;
        this.status = status;
    }
}
