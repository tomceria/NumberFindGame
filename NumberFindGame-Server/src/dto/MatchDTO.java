package dto;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class MatchDTO implements Serializable {
    private int id;
    private String timeStart;
    private double duration;
    private int foundCount;
    private int numberQty;
    private String time;
    private int maxPlayer;

    public MatchDTO(Game_Server game) {
        Time sqlDatetime = Time.valueOf(game.getStartTime());
        this.timeStart = sqlDatetime.toString();
        this.duration = Duration.between(game.getStartTime(), LocalTime.now()).toMillis();
        this.foundCount = game.getCurrentLevel().getValue();
        this.numberQty = game.getMatchConfig().getNumberQty();
        this.time = String.valueOf(game.getMatchConfig().getTime());
        this.maxPlayer = game.getMatchConfig().getMaxPlayer();
    }

    /**
     * @return the timeStart
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart the timeStart to set
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return the duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * @return the foundCount
     */
    public int getFoundCount() {
        return foundCount;
    }

    /**
     * @param foundCount the foundCount to set
     */
    public void setFoundCount(int foundCount) {
        this.foundCount = foundCount;
    }

    /**
     * @return the numberQty
     */
    public int getNumberQty() {
        return numberQty;
    }

    /**
     * @param numberQty the numberQty to set
     */
    public void setNumberQty(int numberQty) {
        this.numberQty = numberQty;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the maxPlayer
     */
    public int getMaxPlayer() {
        return maxPlayer;
    }

    /**
     * @param maxPlayer the maxPlayer to set
     */
    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
