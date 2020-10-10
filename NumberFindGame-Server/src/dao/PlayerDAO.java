package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.PlayerDTO;
import util.MySqlDataAccessHelper;

public class PlayerDAO {
	
	/**
	 * @param rs result set of the query
	 * @return PlayerDTO 
	 */
	private PlayerDTO mapping(ResultSet rs) {
		PlayerDTO player = new PlayerDTO();
		
		try {
			player.setUsername(rs.getString("username"));
			player.setPassword(rs.getString("password"));
			player.setEmail(rs.getString("email"));
			player.setFirstName(rs.getString("first_name"));
			player.setLastName(rs.getString("last_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return player;
	}
	
	/**
	 * @return all players
	 */
	public ArrayList<PlayerDTO> getAll() {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		ArrayList<PlayerDTO> players = new ArrayList<PlayerDTO>();

		String query = "SELECT * FROM players";

		try {
			ResultSet rs = conn.executeQuery(query);
			while (rs.next()) {
				// khởi tạo
				PlayerDTO player = this.mapping(rs);

				// thêm vào array list
				players.add(player);
			}
		} catch (SQLException ex) {
			conn.displayError(ex);
		}

		conn.Close();

		return players;
	}

}
