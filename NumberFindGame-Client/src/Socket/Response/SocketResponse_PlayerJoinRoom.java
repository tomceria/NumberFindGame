package Socket.Response;

import dto.MatchPlayer;

public class SocketResponse_PlayerJoinRoom extends SocketResponse {
    public MatchPlayer clientPlayer_MatchPlayer;

    public SocketResponse_PlayerJoinRoom(MatchPlayer clientPlayer_MatchPlayer) {
        super(SocketResponse.Status.SUCCESS, Action.UPDATE_PLAYERJOINROOM, "Player joining room.");
        this.clientPlayer_MatchPlayer = clientPlayer_MatchPlayer;
        this.status = status;
    }
}
