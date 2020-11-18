package dto;

import java.io.Serializable;

public class GameRoomInfo implements Serializable {
    int id;
    String name;

    public GameRoomInfo(GameRoom gameRoom) {
        this.id = gameRoom.getId();
        this.name = gameRoom.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
