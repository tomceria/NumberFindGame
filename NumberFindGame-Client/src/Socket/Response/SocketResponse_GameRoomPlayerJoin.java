package Socket.Response;

import dto.MatchPlayer;

public class SocketResponse_GameRoomPlayerJoin extends SocketResponse {
    public int gameRoomId;
    public MatchPlayer clientPlayer_MatchPlayer;

    public SocketResponse_GameRoomPlayerJoin(int gameRoomId, MatchPlayer clientPlayer_MatchPlayer) {
        super(SocketResponse.Status.SUCCESS, Action.GAMEROOM_PLAYERJOIN, "Player joining room.");
        this.gameRoomId = gameRoomId;
        this.clientPlayer_MatchPlayer = clientPlayer_MatchPlayer;
        this.status = status;
    }
}
