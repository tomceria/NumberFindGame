package dto;

import Socket.GameClient;
import bus.GameBUS;

import java.awt.*;
import java.util.ArrayList;

public class Game_Client extends Game {
    GameBUS gameBUS;
    GameClient client;
    MatchPlayer clientPlayer;
    ArrayList<LevelNode_Client> level;

    public Game_Client(Game game, GameClient client, MatchPlayer clientPlayer) {
        super(game, false); // Khi Server gửi qua là đã clear => không cần clear nữa
        this.gameBUS = new GameBUS(this);
        this.client = client;
        this.clientPlayer = clientPlayer;
    }

    // Overrides

    @Override
    public void setMatchPlayers(ArrayList<MatchPlayer> players) {
        super.setMatchPlayers(players);
        MatchPlayer_Client.setColorForMatchPlayers(players);
    }

    // Properties

    public GameClient getClient() {
        return client;
    }

    public GameBUS getGameBUS() {
        return gameBUS;
    }

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }
}
