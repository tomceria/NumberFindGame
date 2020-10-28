package dto;

import bus.GameBUS;

import java.awt.*;
import java.util.ArrayList;

public class Game_Client extends Game {
    GameBUS gameBUS;
    MatchPlayer clientPlayer;
    ArrayList<LevelNode_Client> level;

    public Game_Client(Game game, MatchPlayer clientPlayer) {
        super(game, false); // Khi Server gửi qua là đã clear => không cần clear nữa
        this.gameBUS = new GameBUS(this);
        this.clientPlayer = clientPlayer;
    }

    // Overrides

    @Override
    public void setMatchPlayers(ArrayList<MatchPlayer> players) {
        super.setMatchPlayers(players);

        // Set UiColor for MatchPlayers
        ArrayList<Color> colors = new ArrayList<Color>() {{                                       // TODO: Move to Utils
            add(Color.decode("#f73378"));
            add(Color.decode("#ffee33"));
            add(Color.decode("#33bfff"));
            add(Color.decode("#33eb91"));
            add(Color.decode("#ffa733"));
            add(Color.decode("#834bff"));
        }};
        for (int i = 0; i < players.size(); i++) {
            ((MatchPlayer_Client) players.get(i)).uiColor = colors.get(i);
        }
    }

    // Properties

    public GameBUS getGameBUS() {
        return gameBUS;
    }

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }
}
