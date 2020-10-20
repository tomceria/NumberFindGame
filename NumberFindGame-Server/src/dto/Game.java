package dto;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Game {
    MatchConfig matchConfig;
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    LocalTime startTime;

    public MatchConfig getMatchSettings() {
        return matchConfig;
    }
    public void setMatchSettings(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
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
    }

    public ArrayList<LevelNode> getLevel() {
        return level;
    }
    public void setLevel(ArrayList<LevelNode> level) {
        this.level = level;
    }

    public ArrayList<MatchPlayer> getMatchPlayers() {
        return players;
    }
    public void setMatchPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public class CurrentLevel {
        int value;
        LocalTime timeStart;

        public int getValue() {
            return value;
        }
        protected void setValue(int value) {
            this.value = value;
        }

        public LocalTime getTimeStart() {
            return timeStart;
        }
        protected void setTimeStart(LocalTime timeStart) {
            this.timeStart = timeStart;
        }
    }
}
