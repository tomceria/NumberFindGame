package dto;

import java.io.Serializable;

public class MatchConfig implements Serializable {
    private int numberQty;
    private int time;
    private int maxPlayer;

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
