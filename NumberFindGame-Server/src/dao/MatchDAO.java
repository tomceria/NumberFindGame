package dao;

import dto.MatchDTO;
import util.MySqlDataAccessHelper;

import java.sql.Time;

public class MatchDAO {
    /**
     *
     * @param match the new match to insert into database
     */
    public MatchDTO create(MatchDTO match) {
        MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

        String query = "INSERT INTO matches (time_start, duration, found_count, number_quantity, time, max_player) VALUES(?, ?, ?, ?, ?, ?)";

        // prepare statement
        conn.prepare(query);

        // bind values
        int order = 1;
        conn.bind(order++, match.getTimeStart());
        conn.bind(order++, match.getDuration());
        conn.bind(order++, match.getFoundCount());
        conn.bind(order++, match.getNumberQty());
        conn.bind(order++, match.getTime());
        conn.bind(order, match.getMaxPlayer());

        // execute update with prepare statement
        int id = conn.executeUpdatePre();
        match.setId(id);

        conn.Close();

        return match;
    }
}
