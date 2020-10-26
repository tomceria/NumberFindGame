package dto;

public class GameRoom_Client extends GameRoom {
    MatchPlayer clientPlayer;

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(MatchPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }
}
