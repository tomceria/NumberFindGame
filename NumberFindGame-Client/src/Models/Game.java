package Models;

import java.time.LocalTime;
import java.util.ArrayList;

public class Game {
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    MatchPlayer clientPlayer;

    public CurrentLevel getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevelValue) {    // Set will also restart timer
        this.currentLevel = new CurrentLevel();
        this.currentLevel.setValue(currentLevelValue);
        this.currentLevel.setTimeStart(LocalTime.now());
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

    public MatchPlayer getClientPlayer() {
        return clientPlayer;
    }
    public void setClientPlayer(MatchPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
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
