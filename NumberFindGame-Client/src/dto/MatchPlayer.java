package dto;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

public class MatchPlayer implements Serializable {
    private PlayerDTO player;
    private MatchDTO match;
    int score = 0;
    int placing = 0;
    double avgTime = 0;

    public MatchPlayer(PlayerDTO player) {
        this.player = player;
    }

    public MatchPlayer(MatchPlayer matchPlayer) {
        this.player = matchPlayer.player;
        this.match = matchPlayer.match;
        this.score = matchPlayer.score;
        this.placing = matchPlayer.placing;
        this.avgTime = matchPlayer.avgTime;
    }

    public PlayerDTO getPlayer() {
        return player;
    }
    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public MatchDTO getMatch() {
        return match;
    }
    public void setMatch(MatchDTO match) {
        this.match = match;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the placing
     */
    public int getPlacing() {
        return placing;
    }

    /**
     * @param placing the placing to set
     */
    public void setPlacing(int placing) {
        this.placing = placing;
    }

    /**
     * @return the avgTime
     */
    public double getAvgTime() {
        return avgTime;
    }

    /**
     * avgTime cannot be set manually, but instead has to go through this method to assign average time value
     * @param timeStart The starting time for CurrentLevel
     */
    public void newAvgTime(LocalTime timeStart) {
        double time = Duration.between(timeStart, LocalTime.now()).toMillis() * 1.0 / 1000;
        this.avgTime = avgTime <= 0 ? time : (double)(time + avgTime) / 2;
    }

}
