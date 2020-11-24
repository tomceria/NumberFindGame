package bus;

import dao.PlayerDAO;
import dto.PlayerDTO;
import util.BCrypt;

import java.util.ArrayList;

public class PlayerBUS {
    private final PlayerDAO playerDao = new PlayerDAO();

    /**
     * @return all players
     */
    public ArrayList<PlayerDTO> getAll() {
        return playerDao.getAll();
    }

    public PlayerDTO getOneByUsername(String username) {
        PlayerDTO player = playerDao.getByUsername(username);

        return player;
    }

    /**
     * @param player the new player to register
     */
    public boolean register(PlayerDTO player) {
        // hash password
        player.setPassword(BCrypt.hashpw(player.getPassword(), BCrypt.gensalt(12)));
        playerDao.create(player);

        return true;
    }
    
    /**
     * 
     * @param player
     * @return
     */
    public boolean updateInfo(PlayerDTO player) {
        playerDao.updateInfo(player);

        return true;
    }
    
    /**
     * 
     * @param player
     * @return
     */
    public boolean changePassword(String username, String password) {
        // hash password
        password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        playerDao.changePassword(username, password);

        return true;
    }

    /**
     *
     * @param username the login username
     * @param password the login password
     * @return true if username and password is correct, false if username or password is incorrect
     */
    public boolean login(String username, String password) {
        PlayerDTO player = playerDao.getByUsernameOrEmail(username);

        if (player != null) {
            String hashedPassword = player.getPassword();

            // return true if password is matched
            // return false if password is not matched
            return BCrypt.checkpw(password, hashedPassword);
        }

        // user name is incorrect
        return false;
    }
}
