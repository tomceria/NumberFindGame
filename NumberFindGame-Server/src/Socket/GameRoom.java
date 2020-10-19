package Socket;

import dto.PlayerDTO;

public class GameRoom {

    public void joinRoom(PlayerDTO player) {
        System.out.println(player.getEmail() + " joined the room.");
    }
}
