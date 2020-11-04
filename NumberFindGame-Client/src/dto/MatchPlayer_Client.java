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

    // Overrides

    @Override
    public String toString() {
        return String.format("%s: %s", getPlayer().getFirstName(), getScore());
    }

    // Functions

    public static ArrayList<MatchPlayer_Client> castToMatchPlayerClients(ArrayList<MatchPlayer> matchPlayers) {
        ArrayList<MatchPlayer_Client> matchPlayerClients = new ArrayList<MatchPlayer_Client>();
        for (MatchPlayer lN : matchPlayers) {
            matchPlayerClients.add((MatchPlayer_Client) lN);
        }

        return matchPlayerClients;
    }

    public static ArrayList<MatchPlayer> convertMatchPlayersToMatchPlayerClients(ArrayList<MatchPlayer> _matchPlayers) {
        ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();

        for (MatchPlayer matchPlayer : _matchPlayers) {
            MatchPlayer_Client matchPlayerClient = new MatchPlayer_Client(matchPlayer);
            matchPlayers.add(matchPlayerClient);
        }

        return matchPlayers;
    }

    public static ArrayList<MatchPlayer> mergeMatchPlayersAndMatchPlayerClients(ArrayList<MatchPlayer> _matchPlayers, ArrayList<MatchPlayer_Client> matchPlayers_OG) {
        ArrayList<MatchPlayer> matchPlayers = convertMatchPlayersToMatchPlayerClients(_matchPlayers);
        for (int i = 0; i < matchPlayers.size(); i++) {
            MatchPlayer matchPlayer = matchPlayers.get(i);
            MatchPlayer matchPlayer_OG = matchPlayers_OG.get(i);
            ((MatchPlayer_Client) matchPlayer).setUiColor(
                    ((MatchPlayer_Client) matchPlayer_OG).getUiColor()
            );
        }
        return matchPlayers;
    }

    // Properties

    public Color getUiColor() {
        return uiColor;
    }

    public void setUiColor(Color uiColor) {
        this.uiColor = uiColor;
    }
}
