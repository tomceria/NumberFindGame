package dto;

import bus.GameRoomBUS;

import java.util.ArrayList;

public class GameRoom_Client extends GameRoom {
    MatchPlayer clientPlayer;
    ArrayList<MatchPlayer> players;

    public GameRoom_Client(int id) {
        super(id);
        this.gameRoomBUS = new GameRoomBUS(this);
    }

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(MatchPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    public ArrayList<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
    }
}
