package Models;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

public class MatchPlayer {
    Player player;
    int score = 0;
    double avgTime = 0;                                                                                     // in second
    int placing = 1;

    public MatchPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public double getAvgTime() {
        return avgTime;
    }
    public void newAvgTime(LocalTime timeStart) {
        System.out.println(LocalTime.now().getSecond() + "; " + timeStart.getSecond());
        double time = Duration.between(timeStart, LocalTime.now()).toMillis() * 1.0 / 1000;
        this.avgTime = avgTime <= 0 ? time : (double)(time + avgTime) / 2;
    }

    public int getPlacing() {
        return placing;
    }
}
