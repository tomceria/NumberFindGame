package bus;

import dto.GameRoom;

public class GameRoomBUS {
    GameRoom gameRoom;

    public GameRoomBUS(int gameRoomId) {
        this.gameRoom = new GameRoom(gameRoomId);
    }
}
