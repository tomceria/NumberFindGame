package Socket.Response;

import dto.GameRoomStatus;
import dto.MatchConfig;
import dto.MatchPlayer;

import java.util.ArrayList;

public class SocketResponse_GameRoomProps extends SocketResponse {
    public ArrayList<MatchPlayer> players;
    public MatchConfig matchConfig;
    public GameRoomStatus status;

    public SocketResponse_GameRoomProps(ArrayList<MatchPlayer> players, MatchConfig matchConfig, GameRoomStatus status) {
        super(Status.SUCCESS, Action.UPDATE_GAMEROOM, "Update Game Room's state");
        this.players = players;
        this.matchConfig = matchConfig;
        this.status = status;
    }
}
