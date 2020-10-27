package dto;

public class MatchConfig {
    private int numberQty;
    private int time;
    private int maxPlayer;

    /**
     * Init default values
     */
    public MatchConfig() {
        numberQty = 100;
        time = 180000;
        maxPlayer = 3;
    }

    public int getNumberQty() {
        return numberQty;
    }

    public void setNumberQty(int numberQty) {
        this.numberQty = numberQty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }
}
