package bus;

import dao.MatchPlayerDAO;
import dto.LeaderBoard;

import java.util.ArrayList;

public class LeaderBoardBUS {
    private final MatchPlayerDAO mpDao = new MatchPlayerDAO();

    public ArrayList<LeaderBoard> getTopRankings(int x) {
        return mpDao.getLeaderBoard(x, null);
    }

    public LeaderBoard getUserRanking(String username) {
        ArrayList<LeaderBoard> userRaking = mpDao.getLeaderBoard(null, username);
        if (userRaking.size() > 0) {
            return userRaking.get(0);
        }
        return null;
    }
}
