package dto;

import bus.GameBUS;
import util.IChangeListener;

import java.util.ArrayList;

public class Game_Server extends Game {
    GameBUS gameBUS;

    private IChangeListener changeListener;  // Transient Properties

    public Game_Server(MatchConfig matchConfig, ArrayList<MatchPlayer> players) {
        super(matchConfig, players);
        this.gameBUS = new GameBUS(this);
    }

    // Overrides
    @Override
    protected void triggerOnChange() {
        super.triggerOnChange();
        if (changeListener != null) {
            changeListener.onChangeHappened();
        }
    }

    // Properties

    public GameBUS getGameBUS() {
        return gameBUS;
    }

    public void setChangeListener(IChangeListener changeListener) {
        this.changeListener = changeListener;
    }
}
