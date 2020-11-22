package dao;

import dto.LeaderBoard;
import dto.MatchPlayer;
import util.MySqlDataAccessHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatchPlayerDAO {
    /**
     * @param rs kết quả trả về của query
     * @return LeaderBoard
     */
    private LeaderBoard mapToLeaderBoard(ResultSet rs) {
        LeaderBoard leaderBoard = new LeaderBoard();

        try {
            leaderBoard.setRanking(rs.getInt("ranking"));
            leaderBoard.setUsername(rs.getString("username"));
            leaderBoard.setTotalMatches(rs.getInt("total_matches"));
            leaderBoard.setSumRP(rs.getInt("sum_rp"));
            leaderBoard.setWinrate(rs.getDouble("winrate"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderBoard;
    }

    /**
     *
     * @param mp the new match_player to insert into database
     */
    public void create(MatchPlayer mp) {
        MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

        String query = "INSERT INTO match_player (player_id, match_id, score, placing, avg_time, player_count) VALUES(?, ?, ?, ?, ?, ?)";

        // prepare statement
        conn.prepare(query);

        // bind values
        int order = 1;
        conn.bind(order++, mp.getPlayer().getId());
        conn.bind(order++, mp.getMatch().getId());
        conn.bind(order++, mp.getScore());
        conn.bind(order++, mp.getPlacing());
        conn.bind(order++, mp.getAvgTime());
        conn.bind(order, mp.getPlayerCount());

        // execute update with prepare statement
        conn.executeUpdatePre();

        conn.Close();
    }

    public ArrayList<LeaderBoard> getLeaderBoard() {
        MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

        ArrayList<LeaderBoard> leaderBoard = new ArrayList<>();

        String query = "SELECT * FROM leaderboard";

        try {
            ResultSet rs = conn.executeQuery(query);
            while (rs.next()) {
                // create new player
                LeaderBoard result = this.mapToLeaderBoard(rs);

                // add player to array list
                leaderBoard.add(result);
            }
        } catch (SQLException ex) {
            conn.displayError(ex);
        }

        conn.Close();

        return leaderBoard;
    }
}
