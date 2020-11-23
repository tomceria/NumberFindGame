package bus;

import dao.MatchPlayerDAO;
import dto.LeaderBoard;
import dto.PagedResult;

import java.util.ArrayList;

public class LeaderBoardBUS {
    private final MatchPlayerDAO mpDao = new MatchPlayerDAO();

    public ArrayList<LeaderBoard> getLeaderBoard(int page, int pageSize) {
        ArrayList<LeaderBoard> result = mpDao.getLeaderBoard(page, pageSize, null);
        return result;
    }

    public LeaderBoard getUserRanking(String username) {
        ArrayList<LeaderBoard> userRaking = mpDao.getLeaderBoard(1, 1, username);
        if (userRaking.size() > 0) {
            return userRaking.get(0);
        }
        return null;
    }

    public int getLeaderBoardCount() {
        int result = mpDao.getLeaderBoardCount();
        return result;
    }
}
