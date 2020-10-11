package Models;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class Game {
    MatchSettings matchSettings;
    CurrentLevel currentLevel;
    ArrayList<LevelNode> level;
    ArrayList<MatchPlayer> players;
    LocalTime startTime;

    MatchPlayer clientPlayer;                                                              // TODO: Client-only Property

    public MatchSettings getMatchSettings() {
        return matchSettings;
    }
    public void setMatchSettings(MatchSettings matchSettings) {
        this.matchSettings = matchSettings;
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

        System.out.println("NOW FIND: " + getCurrentLevelNodeValue());
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
        // Set UiColor for MatchPlayers
        ArrayList<Color> colors = new ArrayList<Color>() {{                                       // TODO: Move to Utils
            add(Color.RED);
            add(Color.YELLOW);
            add(Color.BLUE);
            add(Color.GREEN);
            add(Color.ORANGE);
            add(Color.PINK);
            add(Color.CYAN);
            add(Color.MAGENTA);
        }};
        for (int i = 0; i < players.size(); i++) {
            players.get(i).uiColor = colors.get(i);
        }

        this.players = players;

    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
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
