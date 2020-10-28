package dto;

import java.awt.*;
import java.util.ArrayList;

public class Game_Client extends Game {
    MatchPlayer clientPlayer;
    ArrayList<LevelNode_Client> level;

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }
    public void setClientPlayer(MatchPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

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
}
