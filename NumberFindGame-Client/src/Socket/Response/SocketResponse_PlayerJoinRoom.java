package Socket.Response;

import dto.MatchPlayer;

public class SocketResponse_PlayerJoinRoom extends SocketResponse {
    public int gameRoomId;
    public MatchPlayer clientPlayer_MatchPlayer;

    public SocketResponse_PlayerJoinRoom(int gameRoomId, MatchPlayer clientPlayer_MatchPlayer) {
        super(SocketResponse.Status.SUCCESS, Action.GAMEROOM_PLAYERJOIN, "Player joining room.");
        this.gameRoomId = gameRoomId;
        this.clientPlayer_MatchPlayer = clientPlayer_MatchPlayer;
        this.status = status;
    }
}
