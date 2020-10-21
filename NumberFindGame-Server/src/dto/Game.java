package dto;

import util.IChangeListener;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Game {
    MatchConfig matchConfig;
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    LocalTime startTime;

    // Transient Properties
    private IChangeListener changeListener;

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }
    public void setMatchConfig(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
        changeListener.onChangeHappened();
    }

    public CurrentLevel getCurrentLevel() {
        return currentLevel;
    }
    public int getCurrentLevelNodeValue() {
        return level
                .get(currentLevel.value - 1)
                .getValue();
    }
    public void setCurrentLevel(int currentLevelValue) {
        this.currentLevel = new CurrentLevel();
        this.currentLevel.setValue(currentLevelValue);
        this.currentLevel.setTimeStart(LocalTime.now());                                  // Set will also restart timer
        changeListener.onChangeHappened();
    }

    public ArrayList<LevelNode> getLevel() {
        return level;
    }
    public void setLevel(ArrayList<LevelNode> level) {
        this.level = level;
        changeListener.onChangeHappened();
    }

    public ArrayList<MatchPlayer> getMatchPlayers() {
        return players;
    }
    public void setMatchPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
        changeListener.onChangeHappened();
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        changeListener.onChangeHappened();
    }

    public void setChangeListener(IChangeListener changeListener) {
        this.changeListener = changeListener;
        changeListener.onChangeHappened();
    }

    public class CurrentLevel {
        int value;
        LocalTime timeStart;

        public int getValue() {
            return value;
        }
        protected void setValue(int value) {
            this.value = value;
            changeListener.onChangeHappened();
        }

        public LocalTime getTimeStart() {
            return timeStart;
        }
        protected void setTimeStart(LocalTime timeStart) {
            this.timeStart = timeStart;
            changeListener.onChangeHappened();
        }
    }
}
