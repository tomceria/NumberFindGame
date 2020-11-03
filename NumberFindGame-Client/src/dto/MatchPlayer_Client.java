package dto;

import java.awt.*;
import java.util.ArrayList;

public class MatchPlayer_Client extends MatchPlayer {
    Color uiColor;

    public MatchPlayer_Client(PlayerDTO player) {
        super(player);
    }

    public MatchPlayer_Client(MatchPlayer matchPlayer) {
        super(matchPlayer);
    }

    public Color getUiColor() {
        return uiColor;
    }

    public void setUiColor(Color uiColor) {
        this.uiColor = uiColor;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getPlayer().getFirstName(), getScore());
    }

    public static ArrayList<MatchPlayer_Client> getMatchPlayerClients(ArrayList<MatchPlayer> matchPlayers) {
        ArrayList<MatchPlayer_Client> matchPlayerClients = new ArrayList<MatchPlayer_Client>();
        for (MatchPlayer lN : matchPlayers) {
            matchPlayerClients.add((MatchPlayer_Client) lN);
        }

        return matchPlayerClients;
    }
}
