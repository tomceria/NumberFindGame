package dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Game implements Serializable {
    // Game does not and should not acknowledge GameRoom
    MatchConfig matchConfig;
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    LocalTime startTime;

    /**
     * True Initializer
     * @param matchConfig
     * @param players
     */
    public Game(MatchConfig matchConfig, ArrayList<MatchPlayer> players) {
        this.matchConfig = matchConfig;
        this.players = players;
    }

    /**
     * Clone instance Game
     * @param game
     * @param willClearServerRefs BẮT BUỘC phải là TRUE nếu muốn serialize instance Game này
     */
    public Game(Game game, boolean willClearServerRefs) {
        this.matchConfig = game.matchConfig;
        this.currentLevel = game.currentLevel;
        this.level = game.level;
        this.players = game.players;
        this.startTime = game.startTime;

        if (willClearServerRefs == true) {
            this.currentLevel = new CurrentLevel(game.currentLevel);
            ArrayList<MatchPlayer> matchPlayers = new ArrayList<MatchPlayer>();
            for (MatchPlayer matchPlayerWithServerRef : game.players) {
                matchPlayers.add(new MatchPlayer(matchPlayerWithServerRef));
            }
            this.players = matchPlayers;
        }
    }

    // Properties

    public MatchConfig getMatchConfig() {
        return matchConfig;
    }
    public void setMatchConfig(MatchConfig matchConfig) {
        this.matchConfig = matchConfig;
    }

    public CurrentLevel getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(CurrentLevel currentLevel) {
        this.currentLevel = currentLevel;
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
}
