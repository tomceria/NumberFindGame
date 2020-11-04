package dto;

public class MatchDTO {
    private int id;
    private String timeStart;
    private double duration;
    private int foundCount;
    private int numberQty;
    private String time;
    private int maxPlayer;

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

}
