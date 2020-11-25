package bus;

import Socket.Request.SocketRequest_LeaderboardAll;
import Socket.Request.SocketRequest_LeaderboardUser;
import dao.MatchPlayerDAO;
import dto.LeaderBoard;
import dto.PagedResult;

import java.util.ArrayList;

public class LeaderBoardBUS {
	private final MatchPlayerDAO mpDao = new MatchPlayerDAO();

	// STATIC (should be controllers???)
	public static PagedResult<LeaderBoard> req_getLeaderBoardResult(SocketRequest_LeaderboardAll request) {
		LeaderBoardBUS leaderBoardBUS = new LeaderBoardBUS();
		int pageSize = 10;
		return new PagedResult<>(leaderBoardBUS.getLeaderBoard(request.page, pageSize), request.page, pageSize,
				leaderBoardBUS.getLeaderBoardCount());
	}

	public static PagedResult<LeaderBoard> req_getLeaderBoardUser(SocketRequest_LeaderboardUser request) {
		LeaderBoardBUS leaderBoardBUS = new LeaderBoardBUS();
		ArrayList<LeaderBoard> result = new ArrayList<>();
		LeaderBoard userRanking = leaderBoardBUS.getUserRanking(request.username);
		if (userRanking != null) {
			result.add(userRanking);
		}
		return new PagedResult<>(result, 1, 1, result.size());
	}

	// Functions

	public ArrayList<LeaderBoard> getLeaderBoard(int page, int pageSize) {
		ArrayList<LeaderBoard> result = mpDao.getLeaderBoard(page, pageSize, null);
		return result;
	}

	public LeaderBoard getUserRanking(String username) {
		ArrayList<LeaderBoard> userRanking = mpDao.getLeaderBoard(1, 1, username);
		if (userRanking.size() > 0) {
			return userRanking.get(0);
		}
		return null;
	}

	public int getLeaderBoardCount() {
		int result = mpDao.getLeaderBoardCount();
		return result;
	}

}
