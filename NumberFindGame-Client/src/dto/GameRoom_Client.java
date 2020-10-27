package dto;

import bus.GameRoomBUS;

public class GameRoom_Client extends GameRoom {
    MatchPlayer clientPlayer;

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
}
