package dao;

import dto.MatchPlayer;
import util.MySqlDataAccessHelper;

public class MatchPlayerDAO {
    /**
     *
     * @param mp the new match_player to insert into database
     */
    public void create(MatchPlayer mp) {
        MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

        String query = "INSERT INTO match_player (player_id, match_id, score, placing, avg_time) VALUES(?, ?, ?, ?, ?)";

        // prepare statement
        conn.prepare(query);

        // bind values
        int order = 1;
        conn.bind(order++, mp.getPlayer().getId());
        conn.bind(order++, mp.getMatch().getId());
        conn.bind(order++, mp.getScore());
        conn.bind(order++, mp.getPlacing());
        conn.bind(order, mp.getAvgTime());

        // execute update with prepare statement
        conn.executeUpdatePre();

        conn.Close();
    }
}
