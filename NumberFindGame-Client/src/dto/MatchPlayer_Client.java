package dto;

import java.awt.*;

public class MatchPlayer_Client extends MatchPlayer {
    Color uiColor;

    public MatchPlayer_Client(PlayerDTO player) {
        super(player);
    }

    public Color getUiColor() {
        return uiColor;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getPlayer().getFirstName(), getScore());
    }
}
