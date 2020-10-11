package Models;

import java.util.ArrayList;

public class Game {
    int currentLevel = 0;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    MatchPlayer clientPlayer;

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public ArrayList<LevelNode> getLevel() {
        return level;
    }
    public void setLevel(ArrayList<LevelNode> level) {
        this.level = level;
    }

    public ArrayList<MatchPlayer> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<MatchPlayer> players) {
        this.players = players;
    }
}
