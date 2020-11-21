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

		ArrayList<PlayerDTO> players = new ArrayList<>();

		String query = "SELECT * FROM players";

		try {
			ResultSet rs = conn.executeQuery(query);
			while (rs.next()) {
				// create new player
				PlayerDTO player = this.mapping(rs);

				// add player to array list
				players.add(player);
			}
		} catch (SQLException ex) {
			conn.displayError(ex);
		}

		conn.Close();

		return players;
	}

	/**
	 *
	 * @param username the username to find
	 * @return a player if found, null if not found
	 */
	public PlayerDTO getByUsername(String username) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		String query = "SELECT * FROM players WHERE username = ?";

		// prepare statement
		conn.prepare(query);

		// bind values
		int order = 1;
		conn.bind(order, username);

		boolean isAny;
		PlayerDTO player = null;
		try {
			ResultSet rs = conn.executeQueryPre();

			// isAny = false if there is no record
			isAny = rs.isBeforeFirst();

			// if there is any record
			if (isAny) {
				rs.next();
				player = this.mapping(rs);
			}
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

		conn.Close();

		return player;
	}

	/**
	 *
	 * @param player the new player to insert into database
	 */
	public void create(PlayerDTO player) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		String query = "INSERT INTO players (username, password, email, first_name, last_name) VALUES(?, ?, ?, ?, ?)";

		// prepare statement
		conn.prepare(query);

		// bind values
		int order = 1;
		conn.bind(order++, player.getUsername());
		conn.bind(order++, player.getPassword());
		conn.bind(order++, player.getEmail());
		conn.bind(order++, player.getFirstName());
		conn.bind(order, player.getLastName());

		// execute update with prepare statement
		conn.executeUpdatePre();

		conn.Close();
	}
	
	public void updateInfo(PlayerDTO player) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		//String query = "INSERT INTO players (username, password, email, first_name, last_name) VALUES(?, ?, ?, ?, ?)";
		String query = "UPDATE players "
					 + "SET first_name = ?,"
					 + "last_name = ?,"
					 + "email = ?"
					 + "WHERE username = ?";

		// prepare statement
		conn.prepare(query);

		// bind values
		int order = 1;
		
		conn.bind(order++, player.getFirstName());
		conn.bind(order++, player.getLastName());
		conn.bind(order++, player.getEmail());
		conn.bind(order, player.getUsername());

		// execute update with prepare statement
		conn.executeUpdatePre();

		conn.Close();
	}
	
	public void changePassword(PlayerDTO player) {
		MySqlDataAccessHelper conn = new MySqlDataAccessHelper();

		String query = "UPDATE players "
					 + "SET password = ?"
					 + "WHERE username = ?";

		// prepare statement
		conn.prepare(query);

		// bind values
		int order = 1;
		
		conn.bind(order++, player.getPassword());
		conn.bind(order, player.getUsername());

		// execute update with prepare statement
		conn.executeUpdatePre();

		conn.Close();
	}

}
