package dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Game implements Serializable {
    MatchConfig matchConfig;
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    LocalTime startTime;

    public Game() {
    }

    /**
     * Clone instance Game
     * @param game
     * @param willIncludeServerRefs BẮT BUỘC phải là TRUE nếu muốn serialize instance Game này
     */
    public Game(Game game, boolean willIncludeServerRefs) {
        this.matchConfig = game.matchConfig;
        this.currentLevel = game.currentLevel;
        this.level = game.level;
        this.players = game.players;
        this.startTime = game.startTime;

        if (willIncludeServerRefs == true) {
            this.currentLevel = new CurrentLevel(game.currentLevel);
            ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();
            for (MatchPlayer matchPlayerWithServerRef : game.players) {
                matchPlayers.add(new MatchPlayer(matchPlayerWithServerRef));
            }
            this.players = matchPlayers;
        }
    }

    public Game(MatchConfig matchConfig, ArrayList<MatchPlayer> players) {
        this.matchConfig = matchConfig;
        this.players = players;
    }

    // Privates

    protected void triggerOnChange() {
        // Empty... should be overriden by Game_Server
    }

    // Properties

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }
    public void setMatchConfig(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
        this.triggerOnChange();
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
        this.triggerOnChange();
    }

    public ArrayList<LevelNode> getLevel() {
        return level;
    }
    public void setLevel(ArrayList<LevelNode> level) {
        this.level = level;
        this.triggerOnChange();
    }

    public ArrayList<MatchPlayer> getMatchPlayers() {
        return players;
    }
    public void setMatchPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
        this.triggerOnChange();
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        this.triggerOnChange();
    }

    public class CurrentLevel implements Serializable {
        int value;
        LocalTime timeStart;

        public CurrentLevel() {
        }
        public CurrentLevel(CurrentLevel currentLevel) {
            this.value = currentLevel.value;
            this.timeStart = currentLevel.timeStart;
        }

        public int getValue() {
            return value;
        }
        protected void setValue(int value) {
            this.value = value;
            Game.this.triggerOnChange();
        }

        public LocalTime getTimeStart() {
            return timeStart;
        }
        protected void setTimeStart(LocalTime timeStart) {
            this.timeStart = timeStart;
            Game.this.triggerOnChange();
        }
    }
}
