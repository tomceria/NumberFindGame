package Socket.Response;

import dto.MatchPlayer;
import dto.PlayerDTO;

import java.util.ArrayList;

public class SocketResponse_GameResult extends SocketResponse {
    public ArrayList<MatchPlayer> matchPlayers;
    public PlayerDTO clientPlayer;
    public PlayerDTO winner;
    public boolean clientPlayerIsWinner;

    public SocketResponse_GameResult(ArrayList<MatchPlayer> matchPlayers, PlayerDTO clientPlayer, PlayerDTO winner, boolean clientPlayerIsWinner) {
        super(Status.SUCCESS, Action.GAME_RESULT, "Returns Game Result.");
        this.matchPlayers = matchPlayers;
        this.clientPlayer = clientPlayer;
        this.winner = winner;
        this.clientPlayerIsWinner = clientPlayerIsWinner;
    }
}
