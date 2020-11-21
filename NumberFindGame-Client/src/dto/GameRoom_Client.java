package dto;

import Socket.GameClient;
import bus.GameRoomBUS;

import java.util.ArrayList;

public class GameRoom_Client extends GameRoom {
    GameClient client; // PARENT
    GameRoomBUS gameRoomBUS;

    ArrayList<MatchPlayer> players;

    public GameRoom_Client(int id, String name, GameClient client) {
        super(id, name);
        this.client = client;
        this.gameRoomBUS = new GameRoomBUS(this);
    }

    // Properties

    public GameClient getClient() {
        return client;
    }

    public GameRoomBUS getGameRoomBUS() {
        return gameRoomBUS;
    }

    public ArrayList<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
    }
}
