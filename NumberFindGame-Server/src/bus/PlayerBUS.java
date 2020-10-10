package bus;

import java.util.ArrayList;

import dao.PlayerDAO;
import dto.PlayerDTO;

public class PlayerBUS {
	private PlayerDAO playerDao = new PlayerDAO();

	/**
	 * @return all players
	 */
	public ArrayList<PlayerDTO> getAll() {
		ArrayList<PlayerDTO> players = playerDao.getAll();
		System.out.print(players.get(0).getFirstName() + " " + players.get(0).getLastName());
		return players;
	}
}
