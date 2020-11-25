package Socket.Response;

import dto.LeaderBoard;
import dto.PagedResult;

public class SocketResponse_LeaderboardResult extends SocketResponse {
    public PagedResult<LeaderBoard> result;

    public SocketResponse_LeaderboardResult(PagedResult<LeaderBoard> result) {
        super(Status.SUCCESS, Action.LEADERBOARD_RESULT, "Leaderboard result.");
        this.result = result;
    }
}
